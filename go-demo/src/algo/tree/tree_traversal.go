package main

import "container/list"

/* 层序遍历 */
func levelOrder(root *TreeNode) []any {
	// 初始化队列，加入根节点
	queue := list.New()
	queue.PushBack(root)
	// 初始化一个切片，用于保存遍历序列
	nums := make([]any, 0)
	for queue.Len() > 0 {
		// 队列出队
		node := queue.Remove(queue.Front()).(*TreeNode)
		// 保存节点值
		nums = append(nums, node.Val)
		if node.Left != nil {
			// 左子节点入队
			queue.PushBack(node.Left)
		}
		if node.Right != nil {
			// 右子节点入队
			queue.PushBack(node.Right)
		}
	}
	return nums
}

/* 前序遍历
void preOrder(TreeNode root) {
if (root == null)
return;
// 访问优先级：根节点 -> 左子树 -> 右子树
list.add(root.val);
preOrder(root.left);
preOrder(root.right);
}

/* 中序遍历
void inOrder(TreeNode root) {
if (root == null)
return;
// 访问优先级：左子树 -> 根节点 -> 右子树
inOrder(root.left);
list.add(root.val);
inOrder(root.right);
}

/* 后序遍历
void postOrder(TreeNode root) {
if (root == null)
return;
// 访问优先级：左子树 -> 右子树 -> 根节点
postOrder(root.left);
postOrder(root.right);
list.add(root.val);
}
*/
