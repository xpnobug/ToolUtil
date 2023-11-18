package com.reai.toolutil.sendEmail.config;

public class Itoo {

    public String title;
    public String date;
    public String welcome;
    public String notice;
    public String logolink;
    public String logo;
    public String teacherlink;
    public String teacherword;
    public String QRcode;
    public String email;

    public String content;

    public Itoo(String title, String date, String welcome, String notice, String logolink,
        String logo,
        String teacherlink, String teacherword, String QRcode, String email, String content) {
        this.title = title;
        this.date = date;
        this.welcome = welcome;
        this.notice = notice;
        this.logolink = logolink;
        this.logo = logo;
        this.teacherlink = teacherlink;
        this.teacherword = teacherword;
        this.QRcode = QRcode;
        this.email = email;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getLogolink() {
        return logolink;
    }

    public void setLogolink(String logolink) {
        this.logolink = logolink;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTeacherlink() {
        return teacherlink;
    }

    public void setTeacherlink(String teacherlink) {
        this.teacherlink = teacherlink;
    }

    public String getTeacherword() {
        return teacherword;
    }

    public void setTeacherword(String teacherword) {
        this.teacherword = teacherword;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
