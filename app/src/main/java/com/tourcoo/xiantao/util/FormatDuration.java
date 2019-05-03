package com.tourcoo.xiantao.util;

/**
 * 将毫秒格式化成 天：小时：分：秒
 */
public class FormatDuration {   

    private static String getString(int t){
        String m="";
        if(t>0){
            if(t<10){
                m="0"+t;
            }else{
                m=t+"";
            }
        }else{
            m="00";
        }
        return m;
    }

    /**
     * 
     * @param t 毫秒
     * @return
     */
    public static String format(int t){
        if(t<60000){
            return (t % 60000 )/1000+"秒";
        }else if((t>=60000)&&(t<3600000)){
            return getString((t % 3600000)/60000)+":"+getString((t % 60000 )/1000);
        }else {
            return getString(t / 3600000)+":"+getString((t % 3600000)/60000)+":"+getString((t % 60000 )/1000);
        }
    }

 /*   public static void main(String[] args) {//测试
        System.out.println(FormatDuration.format(58225670));
    }*/
}