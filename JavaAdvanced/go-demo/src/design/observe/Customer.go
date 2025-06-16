package main

import "fmt"

/*
具体观察者
*/
type Customer struct {
	id string
}

func (c *Customer) Update(id string) {
	fmt.Printf("Sending email to customer %s for item %s\n", c.id, id)
}

func (c *Customer) getID() string {
	return c.id
}

func main() {
	shirtItem := newItem("Nike Shirt")

	observerFirst := &Customer{id: "abc@gmail.com"}
	observerSecond := &Customer{id: "xyz@gmail.com"}
	shirtItem.register(observerFirst)
	shirtItem.register(observerSecond)
	shirtItem.updateAvailability()
}
