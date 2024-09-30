package main

import "fmt"

// Animal 接口定义了一个方法 MakeSound
type Animal interface {
	MakeSound()
}

// Dog 结构体
type Dog struct {
	Name string
}

// MakeSound 实现 Animal 接口的方法
func (d *Dog) MakeSound() {
	fmt.Println(d.Name, "says: Woof!")
}

// Cat 结构体
type Cat struct {
	Name string
}

// MakeSound 实现 Animal 接口的方法
func (c *Cat) MakeSound() {
	fmt.Println(c.Name, "says: Meow!")
}

// Bird 结构体
type Bird struct {
	Name string
}

// MakeSound 实现 Animal 接口的方法
func (b *Bird) MakeSound() {
	fmt.Println(b.Name, "says: Tweet!")
}

type People interface {
	Speak(string) string
}

type student struct{}

func (stu *student) Speak(think string) (talk string) {
	if think == "sb" {
		talk = "你是个大帅比"
	} else {
		talk = "您好"
	}
	return
}

func main() {
	var peo People = &student{}
	think := "bitch"
	fmt.Println(peo.Speak(think))
}
