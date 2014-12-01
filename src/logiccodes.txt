int a=1;
int b=2;
int c=a+b;
对应的指令：
-------------------------------
0: iconst_1 // push 1到操作栈。大于5的int值会用到 bipush <i> 指令。
1: istore_0 // pop 顶元素，存储到index=0的本地变量。
2: iconst_2 // push 2 到操作栈
3: istore_1 // pop栈顶元素，存储到index=1的本地变量。
4: iload_0  // 把index=0的本地变量加载到栈顶
5: iload_1  // 把index=1的本地变量加载到栈顶
6: iadd     // 把栈顶两个数pop出来相加，并把结果存放到栈顶
7: istore_2 // 结果存储到index=2的本地变量