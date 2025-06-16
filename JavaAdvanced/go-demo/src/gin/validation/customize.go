package main

import (
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
	"github.com/go-playground/validator/v10"
	"net/http"
)

/*
对绑定解析到结构体上的参数，自定义验证功能
比如我们要对 name 字段做校验，要不能为空，并且不等于 admin ，类似这种需求，就无法 binding 现成的方法
需要我们自己验证方法才能实现 官网示例（https://godoc.org/gopkg.in/go-playground/validator.v8#hdr-Custom_Functions）
这里需要下载引入下 gopkg.in/go-playground/validator.v8
*/
type Person1 struct {
	Age int `form:"age" binding:"required,gt=10"`
	// 2、在参数 binding 上使用自定义的校验方法函数注册时候的名称
	Name    string `form:"name" binding:"NotNullAndAdmin"`
	Address string `form:"address" binding:"required"`
}

func signValid(fl validator.FieldLevel) bool {
	val := fl.Field().Interface().(string)
	return val != "test" // 例如，这里我们定义了一个简单的规则：用户名不能为"test"
}

func main() {
	r := gin.Default()
	if v, ok := binding.Validator.Engine().(*validator.Validate); ok {
		v.RegisterValidation("sign", signValid)
	}
	/*
	   curl -X GET "http://127.0.0.1:8080/testing?name=&age=12&address=beijing"
	   curl -X GET "http://127.0.0.1:8080/testing?name=lmh&age=12&address=beijing"
	   curl -X GET "http://127.0.0.1:8080/testing?name=adz&age=12&address=beijing"
	*/
	r.GET("/5lmh", func(c *gin.Context) {
		var person Person1
		if e := c.ShouldBind(&person); e == nil {
			c.String(http.StatusOK, "%v", person)
		} else {
			c.String(http.StatusOK, "person bind err:%v", e.Error())
		}
	})
	r.Run()
}
