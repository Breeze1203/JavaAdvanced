package main

import "fmt"

func main() {
	// 定义时调用
	result := func(s string) string {
		return "[" + s + "]"
	}("abc")
	fmt.Println(result)
	fmt.Println("-----------")
	wrap(func(s string) string {
		return "{" + s + "}"
	}, result)()
	fmt.Println("-----------")

	calc := struct {
		add func(int, int) int
		mul func(int, int) int
	}{
		add: func(a, b int) int { return a + b },
		mul: func(a, b int) int { return a * b },
	}
	_ = calc.add(1, 2)

}

func wrap(f func(s string) string, a string) func() {
	return func() {
		println(f(a + "efg"))
	}
}
