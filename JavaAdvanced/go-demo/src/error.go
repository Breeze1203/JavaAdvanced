package main

import (
	"fmt"
)

// 定义一个DivideError结构
type DivideError struct {
	divide  int
	divider int
}

// 实现Error接口
func (de *DivideError) Error() string {
	str := "Cannot proceed,the divider is zero.divide: %divider: 0"
	return fmt.Sprintf(str, de.divide)
}

// 定义int 类型除法运算函数
func Divide(varDivide int, varDivider int) (result int, errorMsg string) {
	if varDivider == 0 {
		dData := DivideError{
			divide:  varDivide,
			divider: varDivider,
		}
		errorMsg = dData.Error()
		return
	} else {
		return varDivide / varDivider, ""
	}
}

func main() {
	// 正常情况
	if result, errorMsg := Divide(100, 10); errorMsg == "" {
		fmt.Println("100/10 = ", result)
	}
	// 当除数为零的时候会返回错误信息
	if _, errorMsg := Divide(100, 0); errorMsg != "" {
		fmt.Println("errorMsg is: ", errorMsg)
	}
}
