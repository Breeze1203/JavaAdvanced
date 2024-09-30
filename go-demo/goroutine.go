package main

import (
	"fmt"
	"math/rand"
)

type Job struct {
	// id
	Id int
	// 需要计数的随机数
	ReadNum int
}

type Result struct {
	// 这里必须传递对象实例
	job *Job
	// 求和
	sum int
}

func main() {
	// 需要两个管道
	// job管道
	jobChan := make(chan *Job, 128)
	// 结果通道
	resultChan := make(chan *Result, 128)
	// 创建工作池
	createPool(64, jobChan, resultChan)
	// 开个打印的写成
	go func(resultChan chan *Result) {
		// 遍历结果管道打印
		for result := range resultChan {
			fmt.Printf("job id:%v randnum:%v result:%d\n", result.job.Id,
				result.job.ReadNum, result.sum)
		}
	}(resultChan)
	var id int
	for {
		id++
		// 生成随机数
		r_num := rand.Int()
		job := &Job{
			Id:      id,
			ReadNum: r_num,
		}
		jobChan <- job
	}
}

// 创建工作池
func createPool(num int, jobChan chan *Job, resultChan chan *Result) {
	// 根据开协程个数，去跑运行
	for i := 0; i < num; i++ {
		go func(jobChan chan *Job, resultChan chan *Result) {
			// 执行运算，遍历job管道索引数据，进行相加
			for job := range jobChan {
				// 随机数结果了
				r_num := job.ReadNum
				// 随机数每一位加，定义返回值
				var sum int
				for r_num != 0 {
					tmp := r_num % 10
					sum += tmp
					r_num /= 10
				}
				// 想要的结果是Result
				r := &Result{job: job, sum: sum}
				resultChan <- r
			}
		}(jobChan, resultChan)
	}
}
