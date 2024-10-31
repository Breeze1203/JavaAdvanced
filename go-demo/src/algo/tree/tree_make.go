package main

/* 二叉树节点结构体 */
type TreeNode struct {
	Val   int
	Left  *TreeNode
	Right *TreeNode
}

/* 构造方法 */
func NewTreeNode(v int) *TreeNode {
	return &TreeNode{
		Left:  nil, // 左子节点指针
		Right: nil, // 右子节点指针
		Val:   v,   // 节点值
	}
}

/*
初始化
*/
func initTree() *TreeNode {
	/* 初始化二叉树 */
	// 初始化节点
	n1 := NewTreeNode(1)
	n2 := NewTreeNode(2)
	n3 := NewTreeNode(3)
	n4 := NewTreeNode(4)
	n5 := NewTreeNode(5)
	// 构建节点之间的引用（指针）
	n1.Left = n2
	n1.Right = n3
	n2.Left = n4
	n2.Right = n5
	return n1
}

func insertOrDelete(n1 *TreeNode, n2 *TreeNode) *TreeNode {
	/* 插入与删除节点 */
	// 在 n1 -> n2 中间插入节点 P
	p := NewTreeNode(0)
	n1.Left = p
	p.Left = n2
	// 删除节点 P
	n1.Left = n2
	return n1
}
