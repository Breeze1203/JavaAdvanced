package org.pt.design.factorydesign;

import java.io.ObjectInputFilter;

public class Application {
    private Dialog dialog;

    // 程序根据当前配置或环境设定选择创建者的类型。
    void initialize() {
        // 假设readApplicationConfigFile()返回Config类型的对象，Config类包含OS属性。
        Config config = readApplicationConfigFile();
        if (config.getOS().equals("Windows")) {
            dialog = new WindowsDialog();
        } else if (config.getOS().equals("Web")) {
            dialog = new WebDialog();
        } else {
            System.out.println(config.getOS());
        }
    }
    // 当前客户端代码会与具体创建者的实例进行交互，但是必须通过其基本接口
    // 进行。只要客户端通过基本接口与创建者进行交互，你就可将任何创建者子
    // 类传递给客户端。
    void main() {
        this.initialize();
        dialog.render();
    }

    // 假设的Config类
    private static class Config {
        private String OS;

        public String getOS() {
            return OS;
        }

        public Config() {
            this.OS = System.getProperty("os.name");
        }
    }
    // 假设的读取配置文件方法
    private Config readApplicationConfigFile() {
        // 读取配置文件并返回Config对象的逻辑
        return new Config();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.main();
    }
}
