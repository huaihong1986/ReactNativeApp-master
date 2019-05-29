package com.example.song.reactnativeappdemo.hotupdate;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * author : Aihong
 * e-mail : huaihong1986@sina.com
 * date   : 2019/5/2820:18
 * desc   :
 * version: 1.0
 */
public class Patch {
    public static void main(String[] args) {
        patch();
    }
    @TargetApi(Build.VERSION_CODES.O)
    public static void patch() {
        // 获取新旧Bundle文件
        String o = getStringFromPat("C:/Users/huaihong/Desktop/old.bundle");
        String n = getStringFromPat("C:/Users/huaihong/Desktop/new.bundle");

        // 对比
        diff_match_patch dmp = new diff_match_patch();
        LinkedList<diff_match_patch.Diff> diffs = dmp.diff_main(o, n);

        // 生成差异补丁包
        LinkedList<diff_match_patch.Patch> patches = dmp.patch_make(diffs);

        // 解析补丁包
        String patchesStr = dmp.patch_toText(patches);

        try {
            // 将补丁文件写入到某个位置
            Files.write(Paths.get("C:/Users/huaihong/Desktop/bundle.pat"), patchesStr.getBytes());
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 将.pat文件转换为String
     * @param patPath 下载的.pat文件所在目录
     * @return
     */
    public static String getStringFromPat(String patPath) {

        FileReader reader = null;
        String result = "";
        try {
            reader = new FileReader(patPath);
            int ch = reader.read();
            StringBuilder sb = new StringBuilder();
            while (ch != -1) {
                sb.append((char)ch);
                ch  = reader.read();
            }
            reader.close();
            result = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
