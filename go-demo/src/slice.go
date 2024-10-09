package main

import "fmt"

//变量的声明可以同时包括初始化操作。
//变量的声明和初始化可以在包级别（全局变量）或函数内部进行。
//赋值操作是指将一个已经声明的变量更新为新的值，在Go语言中，这种操作必须在函数体内部执行

/*
切片demo
组成要素：
指针：指向底层数组
长度：切片中元素的长度，不能大于容量
容量：指针所知晓的底层数组的总容量
*/

// 常见初始化放松
// 使用make初始化
//slice1 :=make([]int,5) //初始化长度和容量都为5的切片
//slice2 :=make([]int,5,10) //初始化长度为5，容量为10的切片
// 使用简短定义
//slice3 :=[]int{1,2,3,4,5}
// 使用数组来初始化切片
//arr :=[5]int{1,2,3,4,5}
//slice :=arr[0:3]

//使用切片来初始化切片
//sliceA := []int{1, 2, 3, 4, 5}
//sliceB := sliceA[0:3] // 左闭右开区间，sliceB 最终为 [1,2,3]

/*
长度和容量
*/
func main() {
	sliceC := []int{1, 2, 3, 4, 5}
	fmt.Println("len: ", len(sliceC))
	fmt.Println("cap: ", cap(sliceC))

	//改变切片长度
	sliceC = append(sliceC, 6)
	fmt.Println("after append operation: ")
	fmt.Println("len: ", len(sliceC))
	fmt.Println("cap: ", cap(sliceC)) //注意，底层数组容量不够时，会重新分配数组空间，通常为两倍，所以打印为10

	slice := []int{1, 2, 3, 4, 5}
	newSlice := slice[0:3]
	fmt.Println("before modifying underlying array:")
	fmt.Println("slice: ", slice)
	fmt.Println("newSlice: ", newSlice)
	fmt.Println()

	newSlice[0] = 6
	fmt.Println("after modifying underlying array:")
	fmt.Println("slice: ", slice)
	fmt.Println("newSlice: ", newSlice)

	sliceA := []int{1, 2, 3, 4, 5}
	// 初始化newSliceB为长度为5的切片
	newSliceB := make([]int, len(sliceA))
	// 将sliceA的内容复制到newSliceB中
	copy(newSliceB, sliceA)
	fmt.Println("before modifying underlying array:")
	fmt.Println("sliceA: ", sliceA)
	fmt.Println("newSliceB: ", newSliceB)
	fmt.Println()

	newSliceB[0] = 6
	fmt.Println("after modifying underlying array:")
	fmt.Println("slice: ", sliceA)
	fmt.Println("newSlice: ", newSliceB)

	// 假设切片 slice 如下:
	sliceD := []int{1, 2, 3, 4, 5}
	// 如何使用 copy 创建切片 newSlice, 该切片值为 [2, 3, 4]
	newSliceE := make([]int, 3)
	copy(newSliceE, sliceD[1:4])
	fmt.Println("newSliceE", newSliceE)

	a := [...]int{1, 2, 3, 4, 5, 7, 8, 9}
	s := a[2:6:8]
	//切片操作 a[start:end:cap] 包含三个参数：
	//start：切片开始的索引（包含该索引对应的元素）。
	//end：切片结束的索引（不包含该索引对应的元素）。
	//cap：切片的容量，即从开始索引到原数组末尾的元素数量
	fmt.Println(s)
	fmt.Println(len(s), cap(s))
	// 2  6
}
