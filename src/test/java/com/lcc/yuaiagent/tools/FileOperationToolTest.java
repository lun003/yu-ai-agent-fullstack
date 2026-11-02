package com.lcc.yuaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FileOperationToolTest {

    @Test
    void redFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "编程导航.txt";
        String result = tool.redFile(fileName);
        assertNotNull(result);
    }

    @Test
    void writeFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "编程导航.txt";
        String Content = "https://www.codefather.cn 程序员编程学习交流社区";
        String result = tool.writeFile(fileName,Content);
        assertNotNull(result);
    }
}