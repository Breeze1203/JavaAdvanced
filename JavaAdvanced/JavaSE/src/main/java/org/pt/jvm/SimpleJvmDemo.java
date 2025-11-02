package org.pt.jvm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SimpleJvmDemo {

    // ---------------------------------------------------------------
    // 模拟内存槽位 (Slot) - 用于区分 "值" 和 "引用"
    // ---------------------------------------------------------------
    static class Slot {
        int value; // 如果是基本类型, 存值; 如果是引用, 存 "地址"
        boolean isReference;

        Slot(int value, boolean isReference) {
            this.value = value;
            this.isReference = isReference;
        }

        @Override
        public String toString() {
            return isReference ? "Ref(@" + value + ")" : String.valueOf(value);
        }
    }

    // ---------------------------------------------------------------
    // 模拟 "方法区" (部分) - 存储类和方法
    // ---------------------------------------------------------------
    static class ClassInfo { // 类的“图纸”
        String name;
        Map<String, Integer> fieldOffsets = new HashMap<>(); // 字段名 -> 偏移量
        int fieldCount = 0;

        ClassInfo(String name) { this.name = name; }

        void addField(String fieldName) {
            fieldOffsets.put(fieldName, fieldCount++);
        }
        int getFieldOffset(String fieldName) { return fieldOffsets.get(fieldName); }
    }

    // 用于存储所有加载的类信息
    static class ClassArea {
        Map<String, ClassInfo> classes = new HashMap<>();
        public void loadClass(ClassInfo ci) { classes.put(ci.name, ci); }
        public ClassInfo findClass(String name) { return classes.get(name); }
    }

    static class MethodInfo {
        String name;
        List<String> instructions;
        int maxLocals;
        MethodInfo(String name, List<String> instructions, int maxLocals) {
            this.name = name; this.instructions = instructions; this.maxLocals = maxLocals;
        }
    }

    // 存储所有方法信息
    static class MethodArea {
        Map<String, MethodInfo> methods = new HashMap<>();
        public void loadMethod(MethodInfo method) { methods.put(method.name, method); }
        public MethodInfo findMethod(String name) { return methods.get(name); }
    }

    // ---------------------------------------------------------------
    // 模拟 "堆" (Heap) 和 "对象实例"
    // ---------------------------------------------------------------
    static class ObjectInstance {
        ClassInfo classInfo;
        Slot[] fields; // 存储实例字段数据

        ObjectInstance(ClassInfo classInfo) {
            this.classInfo = classInfo;
            this.fields = new Slot[classInfo.fieldCount];
        }
    }

    static class Heap {
        Map<Integer, ObjectInstance> memory = new HashMap<>();
        int nextAddress = 1; // 0 代表 null

        // 分配对象
        public int allocate(ObjectInstance obj) {
            int address = nextAddress;
            memory.put(address, obj);
            nextAddress++;
            System.out.println("    [堆] 分配对象 " + obj.classInfo.name + " at @" + address);
            return address;
        }

        // 访问对象
        public ObjectInstance get(int address) {
            return memory.get(address);
        }
    }

    // ---------------------------------------------------------------
    // 模拟 "虚拟机栈" 和 "栈帧" (现在使用 Slot)
    // ---------------------------------------------------------------
    static class StackFrame {
        Slot[] localVariables;          // 局部变量表 (现在是 Slot 数组)
        Stack<Slot> operandStack;       // 操作数栈 (现在是 Slot 栈)
        MethodInfo methodInfo;
        int pc = 0;                     // PC寄存器(简化版)

        StackFrame(MethodInfo methodInfo) {
            this.methodInfo = methodInfo;
            this.localVariables = new Slot[methodInfo.maxLocals];
            this.operandStack = new Stack<>();
        }
        public String getCurrentInstruction() { return methodInfo.instructions.get(pc); }
    }

    static class JvmStack {
        Stack<StackFrame> frames = new Stack<>();
        public void push(StackFrame frame) { frames.push(frame); }
        public StackFrame pop() { return frames.pop(); }
        public StackFrame peek() { return frames.peek(); }
        public boolean isEmpty() { return frames.isEmpty(); }
    }

    // =========================================================
    // 模拟 JVM 和 执行引擎
    // =========================================================
    private JvmStack jvmStack;
    private MethodArea methodArea;
    private ClassArea classArea;
    private Heap heap;
    // 缺失: 本地方法栈 (Native Method Stack)

    public SimpleJvmDemo() {
        // 这五个组件就是JVM内存模型的主要体现
        this.jvmStack = new JvmStack();      // 虚拟机栈
        this.methodArea = new MethodArea();  // 方法区 (存方法)
        this.classArea = new ClassArea();    // 方法区 (存类结构)
        this.heap = new Heap();              // 堆
        // PC 寄存器在 StackFrame 内部 (pc 变量)
        // 本地方法栈未模拟
    }

    public void loadProgram() {
        // 加载 "Person" 类
        ClassInfo personClass = new ClassInfo("Person");
        personClass.addField("age"); // Person 有一个 'age' 字段
        classArea.loadClass(personClass);

        // 模拟 main()
        // 伪字节码:
        // NEW Person    (创建p, 引用压栈)
        // STORE_REF 0   (p存入局部变量[0])
        // LOAD_REF 0    (加载p的引用)
        // PUSH 30       (加载30)
        // PUTFIELD age  (p.age = 30)
        // LOAD_REF 0    (加载p的引用)
        // GETFIELD age  (加载p.age, 即30)
        // STORE 1       (存入局部变量[1], 即a)
        // LOAD 1        (加载a)
        // PRINT         (打印a)
        // VRETURN
        MethodInfo mainMethod = new MethodInfo("main",
                Arrays.asList(
                        "NEW Person",
                        "STORE 0",  // 对应V1的STORE_REF
                        "LOAD 0",   // 对应V1的LOAD_REF
                        "PUSH 30",
                        "PUTFIELD age",
                        "LOAD 0",   // 对应V1的LOAD_REF
                        "GETFIELD age",
                        "STORE 1",  // 对应V1的STORE
                        "LOAD 1",   // 对应V1的LOAD
                        "PRINT",
                        "VRETURN"
                ), 2); // 局部变量表大小为2 (p, a)

        methodArea.loadMethod(mainMethod);
        System.out.println("✅ V2 程序加载完毕！(包含 'Person' 类和 'main' 方法)");
        System.out.println("------------------------------------");
    }

    public void run() {
        System.out.println("🚀 V2 JVM 启动，开始执行 'main' 方法...");
        StackFrame mainFrame = new StackFrame(methodArea.findMethod("main"));
        jvmStack.push(mainFrame);

        while (!jvmStack.isEmpty()) {
            StackFrame currentFrame = jvmStack.peek();
            String instruction = currentFrame.getCurrentInstruction();
            currentFrame.pc++;

            executeInstruction(instruction, currentFrame);
        }
        System.out.println("------------------------------------");
        System.out.println("🏁 'main' 方法执行完毕，JVM 关闭。");
    }

    private void executeInstruction(String instruction, StackFrame currentFrame) {
        String[] parts = instruction.split(" ");
        String opcode = parts[0];

        System.out.println(" [执行] -> " + instruction);

        switch (opcode) {
            case "PUSH":
                currentFrame.operandStack.push(new Slot(Integer.parseInt(parts[1]), false));
                break;
            case "STORE": // STORE 和 STORE_REF 简化合并
                currentFrame.localVariables[Integer.parseInt(parts[1])] = currentFrame.operandStack.pop();
                break;
            case "LOAD": // LOAD 和 LOAD_REF 简化合并
                currentFrame.operandStack.push(currentFrame.localVariables[Integer.parseInt(parts[1])]);
                break;
            case "PRINT":
                Slot printVal = currentFrame.operandStack.pop();
                System.out.println("    ***************");
                System.out.println("    * [输出] " + printVal.value);
                System.out.println("    ***************");
                break;
            case "VRETURN":
                System.out.println("    (弹出栈帧: " + currentFrame.methodInfo.name + ")");
                jvmStack.pop();
                break;

            // --- (堆操作) ---
            case "NEW":
                // 1. 找到类“图纸”
                ClassInfo classInfo = classArea.findClass(parts[1]);
                // 2. 创建实例
                ObjectInstance newObj = new ObjectInstance(classInfo);
                // 3. 在堆上分配内存, 拿到 "地址"
                int address = heap.allocate(newObj);
                // 4. 将 "地址"(引用) 包装成 Slot 压入操作数栈
                currentFrame.operandStack.push(new Slot(address, true));
                break;

            case "PUTFIELD":
                // 1. 弹出要设置的 '值'
                Slot value = currentFrame.operandStack.pop();
                // 2. 弹出 '对象引用' (地址)
                Slot objRef = currentFrame.operandStack.pop();
                // 3. 去堆上找到该对象
                ObjectInstance objToSet = heap.get(objRef.value);
                // 4. 找到字段偏移量
                int offsetSet = objToSet.classInfo.getFieldOffset(parts[1]);
                // 5. 设置字段值
                objToSet.fields[offsetSet] = value;
                System.out.println("    [堆] 设置 Ref@" + objRef.value + "." + parts[1] + " = " + value.value);
                break;

            case "GETFIELD":
                // 1. 弹出 '对象引用' (地址)
                Slot objRefGet = currentFrame.operandStack.pop();
                // 2. 去堆上找到该对象
                ObjectInstance objToGet = heap.get(objRefGet.value);
                // 3. 找到字段偏移量
                int offsetGet = objToGet.classInfo.getFieldOffset(parts[1]);
                // 4. 读取字段值
                Slot valueGet = objToGet.fields[offsetGet];
                // 5. 将 '值' 压入操作数栈
                currentFrame.operandStack.push(valueGet);
                System.out.println("    [堆] 读取 Ref@" + objRefGet.value + "." + parts[1] + " (值=" + valueGet.value + ")");
                break;
        }

        // 调试信息
         System.out.println("    | OpStack: " + currentFrame.operandStack);
         System.out.println("    | Locals: " + Arrays.toString(currentFrame.localVariables));
    }

    public static void main(String[] args) {
        SimpleJvmDemo jvm = new SimpleJvmDemo();
        jvm.loadProgram();
        jvm.run();
    }
}