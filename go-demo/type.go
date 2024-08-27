package main

import "fmt"

//var a int
//var b bool
//
//var (
//	c, d int
//) //同时什么a,b的整数

func main() {
	// 定义长度为 5 的数组
	var arr1 [5]int
	for i := 0; i < 5; i++ {
		arr1[i] = i
	}
	printHelper("arr1", arr1)
	// 以下赋值会报类型不匹配错误，因为数组长度是数组类型的一部分
	// arr1 = [3]int{1, 2, 3}
	arr1 = [5]int{2, 3, 4, 5, 6}
	arr2 := [5]int{3, 4, 5, 6, 7}
	printHelper("arr2", arr2)
	// 数组元素类型相同，且长度相同可以比较
	fmt.Println(arr2 == arr1)
	// 也可以不显式定义数组长度，由编译器完成长度计算
	var arr3 = [...]int{0, 1, 2, 3, 4}
	printHelper("arr3", arr3)
	// 定义前四个元素为默认值 0，最后一个元素为 -1
	var arr4 = [...]int{4: -1}
	printHelper("arr4", arr4)
	// 多维数组
	var twoD [2][3]int
	for i := 0; i < 2; i++ {
		for j := 0; j < 3; j++ {
			twoD[i][j] = i + j
		}
	}
	fmt.Println("twoD: ", twoD)
}

func printHelper(name string, arr [5]int) {
	for i := 0; i < len(arr); i++ {
		fmt.Println(name, arr[i])
	}
	// len获取长度
	fmt.Print(len(arr))
	// cap也可以用来获取长度
	fmt.Print("长度为", cap(arr))
	fmt.Println()
}
