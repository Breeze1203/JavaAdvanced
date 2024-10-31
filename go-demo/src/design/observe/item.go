package main

import "fmt"

type Item struct {
	observerList []Observer
	name         string
	isStock      bool
}

func newItem(name string) *Item {
	return &Item{
		name: name,
	}
}

/*
订阅
*/
func (item *Item) register(o Observer) {
	item.observerList = append(item.observerList, o)
}

/*
更新
*/
func (item *Item) updateAvailability() {
	fmt.Printf("Item %s is now in stock\n", item.name)
	item.isStock = true
	item.notifyAll()
}

/*
取消订阅
*/
func (item *Item) unregister(o Observer) {
	item.observerList = removeFromSlice(item.observerList, o)
}

func (item *Item) notifyAll() {
	for _, observer := range item.observerList {
		observer.Update(item.name)
	}
}

/*
从切片中中移除
*/
func removeFromSlice(observerList []Observer, observer Observer) []Observer {
	l := len(observerList)
	for i := 0; i < l; i++ {
		if observer.getID() == observerList[i].getID() {
			observerList[i] = observerList[l-1]
			observerList[l-1] = observer
			return observerList[:l-1]
		}
	}
	return observerList
}
