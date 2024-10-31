package main

import "fmt"

func A(a []int) {
	a[0] = 100
	fmt.Print(a)
}
func B(b *int) {
	*b = 6
	fmt.Println(*b)
}

func main() {
	a := []int{1, 2, 3}
	A(a)
	fmt.Println(a)
	b := 4
	B(&b)
	fmt.Println(b)
}
