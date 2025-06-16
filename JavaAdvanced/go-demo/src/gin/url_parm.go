package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

// 地址http://localhost:8080/user?name=%E5%BC%A0%E4%B8%89
func main() {
	engine := gin.Default()
	engine.GET("/user", func(c *gin.Context) {
		name := c.DefaultQuery("name", "枯藤")
		c.String(http.StatusOK, fmt.Sprintf("hello %s", name))
	})
	engine.Run()
}
