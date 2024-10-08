package main

import (
	"context"
	"fmt"
	"github.com/redis/go-redis"
)

func main() {
	redis := redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "pt200103", // 没有密码，默认值
		DB:       0,          // 默认DB 0
	})
	ctx := context.Background()
	val, _ := redis.Get(ctx, "key").Result()
	fmt.Println(val)
}
