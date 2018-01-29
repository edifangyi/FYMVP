package com.fangyi.sample.mvp.bean;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：
 * ================================================
 */
public class LoginBean {
    @Override
    public String toString() {
        return "LoginBean{" +
                "\n    name='" + name + '\'' +
                ",\n   age=" + age +
                ",\n   QQ='" + QQ + '\n' +
                '}';
    }

    /**
     * name : 小别野
     * age : 23
     * QQ : 87649669
     */

    private String name;
    private int age;
    private String QQ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }
}
