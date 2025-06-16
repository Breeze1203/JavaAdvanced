package main

import (
	"bytes"
	"fmt"
	"reflect"
	"strconv"
	"unicode/utf8"
)

// 将任意值转换为JSON格式的字符串
func toJSON(v interface{}) (string, error) {
	var buffer bytes.Buffer
	err := serializeValue(reflect.ValueOf(v), &buffer)
	if err != nil {
		return "", err
	}
	return buffer.String(), nil
}

// 递归序列化值
func serializeValue(v reflect.Value, buffer *bytes.Buffer) error {
	switch v.Kind() {
	case reflect.Struct:
		// 开始序列化结构体
		buffer.WriteString("{")
		first := true
		for i := 0; i < v.NumField(); i++ {
			if !first {
				buffer.WriteString(", ")
			}
			field := v.Type().Field(i)
			buffer.WriteString(fmt.Sprintf("\"%s\": ", field.Name))
			err := serializeValue(v.Field(i), buffer)
			if err != nil {
				return err
			}
			first = false
		}
		buffer.WriteString("}")
	case reflect.String:
		// 序列化字符串
		buffer.WriteString(fmt.Sprintf("\"%s\"", escapeString(v.String())))
	case reflect.Int, reflect.Int8, reflect.Int16, reflect.Int32, reflect.Int64:
		print("整数：" + fmt.Sprintf("\"%s\"", escapeString(v.String())))
		// 序列化整数
		buffer.WriteString(strconv.FormatInt(v.Int(), 10))
	case reflect.Float32, reflect.Float64:
		// 序列化浮点数
		buffer.WriteString(strconv.FormatFloat(v.Float(), 'f', -1, 64))
	case reflect.Bool:
		// 序列化布尔值
		if v.Bool() {
			buffer.WriteString("true")
		} else {
			buffer.WriteString("false")
		}
	case reflect.Slice, reflect.Array:
		// 序列化切片或数组
		buffer.WriteString("[")
		for i := 0; i < v.Len(); i++ {
			if i > 0 {
				buffer.WriteString(", ")
			}
			err := serializeValue(v.Index(i), buffer)
			if err != nil {
				return err
			}
		}
		buffer.WriteString("]")
	default:
		return fmt.Errorf("unsupported type: %v", v.Type())
	}
	return nil
}

// 对字符串进行转义，以确保生成有效的JSON字符串
func escapeString(s string) string {
	var buffer bytes.Buffer
	for i := 0; i < len(s); {
		r, size := utf8.DecodeRuneInString(s[i:])
		switch r {
		case '"', '\\':
			buffer.WriteRune('\\')
			buffer.WriteRune(r)
		case '\n':
			buffer.WriteString("\\n")
		case '\r':
			buffer.WriteString("\\r")
		case '\t':
			buffer.WriteString("\\t")
		default:
			buffer.WriteRune(r)
		}
		i += size
	}
	return buffer.String()
}

func main() {
	// 创建一个Person实例
	person := struct {
		Name string
		Age  int
	}{Name: "Alice", Age: 30}

	// 将Person实例转换为JSON字符串
	jsonStr, err := toJSON(person)
	if err != nil {
		fmt.Println("Error:", err)
		return
	}

	// 打印JSON字符串
	fmt.Println(jsonStr)
}
