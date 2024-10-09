package main

import "fmt"

type Person1 struct {
	Name  string
	Power int
}

type Student struct {
	Name  string
	Age   int
	Score int
}

// 声明和初始化
func main() {
	goku := &Person1{
		Name:  "PengTao",
		Power: 22,
	}
	goku.Power = 30
	print(goku.Name)
	Super(goku)
	fmt.Println(goku.Power)

	student := Student{
		Name:  "Jack",
		Age:   22,
		Score: 10,
	}
	student.Super()
	fmt.Println(student.Score)

	newStudent := NewStudent("废物", 23, 79)
	fmt.Println(newStudent.Score)
	newStudent.Name = "李四是废物"
	fmt.Println(newStudent.Name)
}

func Super(s *Person1) {
	s.Power += 10000
}

func (s *Student) Super() {
	s.Score += 90
}

func NewStudent(name string, age int, power int) Student {
	return Student{
		Name:  name,
		Age:   age,
		Score: power,
	}
}
