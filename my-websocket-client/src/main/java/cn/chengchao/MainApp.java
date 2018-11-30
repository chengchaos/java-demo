package cn.chengchao;

import cn.chengchao.helper.Cons;
import cn.chengchao.items.UseLineInput;

/**
 * Hello world!
 */
public class MainApp {




    public static void main(String[] args) {

        new UseLineInput().execute(Cons.CLIENT_URL);

        System.exit(0);


    }
}
