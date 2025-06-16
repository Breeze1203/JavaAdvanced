package main

/*
通用观察者接口
*/
type Observer interface {
	Update(string)
	getID() string
}
