package main

// 定义一个方法参数是泛型T,返回类型也是T
func test[T any](v T) T {
	return v
}

func main() {
	test(1)
	type x int
	test(x(2))
	test("abc")
	Test(N{})
}

type Tester interface {
	test()
	string() string
}

type N struct{}

func (N) test() {
	println("test!")
}
func (N) string() string {
	return "N"
}

func Test[T Tester](v T) {
	s := v.string()
	println(s)
}
