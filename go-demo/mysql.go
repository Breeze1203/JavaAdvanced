package main

import (
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jmoiron/sqlx"
)

type Person struct {
	UserId   int    `db:"user_id"`
	Username string `db:"username"`
	Sex      string `db:"sex"`
	Email    string `db:"email"`
}

type Place struct {
	Country string `db:"country"`
	City    string `db:"city"`
	TelCode int    `db:"telcode"`
}

var Db *sqlx.DB

func init() {
	database, err := sqlx.Open("mysql", "root:3548297839e@tcp(localhost:3306)/jpa")
	if err != nil {
		fmt.Println("open mysql failed,", err)
		return
	}
	Db = database
}

func main() {
	fmt.Println("------------------插入---------------")
	r, err := Db.Exec("insert into person(username, sex, email)values(?, ?, ?)", "stu001", "man", "stu01@qq.com")
	if err != nil {
		fmt.Println("exec failed, ", err)
		return
	}
	id, err := r.LastInsertId()
	if err != nil {
		fmt.Println("exec failed, ", err)
		return
	}

	fmt.Println("insert succ:", id)
	fmt.Println("------------------查询---------------")
	var person []Person
	err1 := Db.Select(&person, "select user_id, username, sex, email from person where user_id=?", 8)
	// 这里不为null，则说明发生了错误
	if err1 != nil {
		fmt.Println("exec failed, ", err1)
		return
	}
	if len(person) == 0 {
		fmt.Println("No records found")
	} else {
		fmt.Println("select success:", person)
	}

	fmt.Println("------------------修改---------------")
	res, err := Db.Exec("update person set username=? where user_id=?", "stu0003", 7)
	if err != nil {
		fmt.Println("exec failed, ", err)
		return
	}
	row, err := res.RowsAffected()
	if err != nil {
		fmt.Println("rows failed, ", err)
	}
	fmt.Println("update succ:", row)

	fmt.Println("------------------删除---------------")

	result, err := Db.Exec("delete from person where user_id=?", 1)
	if err != nil {
		fmt.Println("exec failed, ", err)
		return
	}
	affected, err := result.RowsAffected()
	if err != nil {
		fmt.Println("rows failed, ", err)
	}

	fmt.Println("delete succ: ", affected)
}
