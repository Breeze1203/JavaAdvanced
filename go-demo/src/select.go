package main

import (
	"fmt"
	"time"
)

func test1(ch chan string) {
	time.Sleep(time.Second * 5)
	ch <- "test1"
}

func test2(ch chan string) {
	time.Sleep(time.Second * 2)
	ch <- "test2"
}

func main() {
	output1 := make(chan string)
	output2 := make(chan string)
	// 用两个协程跑数据
	go test1(output1)
	go test2(output2)
	// 用select监听
	for {
		select {
		case s1 := <-output1:
			fmt.Println("s1=", s1)
		case s2 := <-output2:
			fmt.Println("s2=", s2)
		}
	}
}

// 如果多个channel同时ready，则随机选择一个执行
func main2() {
	// 创建2个管道
	int_chan := make(chan int, 1)
	string_chan := make(chan string, 1)
	go func() {
		//time.Sleep(2 * time.Second)
		int_chan <- 1
	}()
	go func() {
		string_chan <- "hello"
	}()
	select {
	case value := <-int_chan:
		fmt.Println("int:", value)
	case value := <-string_chan:
		fmt.Println("string:", value)
	}
	fmt.Println("main结束")
}

// 判断管道有没有存满
func main3() {
	// 创建管道
	output1 := make(chan string, 10)
	// 子协程写数据
	go write2(output1)
	// 取数据
	for s := range output1 {
		fmt.Println("res:", s)
		time.Sleep(time.Second)
	}
}

func write2(ch chan string) {
	for {
		select {
		// 写数据
		case ch <- "hello":
			fmt.Println("write hello")
		default:
			fmt.Println("channel full")
		}
		time.Sleep(time.Millisecond * 500)
	}
}
