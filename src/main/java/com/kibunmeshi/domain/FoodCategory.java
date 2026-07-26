package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 料理カテゴリ（FoodCategory）エンティティ
 * 
 * ユーザーが選択できる5種類の料理カテゴリを表現するドメインモデル。
 * 🥘 韓国料理、🍔 洋食、🥟 中華、🍣 和食、🍕 その他
 * 
 * @author 作者名
 * @version 1.0.0
 */
@Data
public class FoodCategory {
    
    /**
     * カテゴリID（1-5）
     * 主キー、自動採番
     */
    private Integer id;
    
    /**
     * 英語名（korean, western, chinese, japanese, others）
     * システム内部で使用する識別子
     */
    private String name;
    
    /**
     * 日本語ラベル（韓国料理、洋食...）
     * 画面表示用の日本語文字列
     */
    private String label;
    
    /**
     * アイコン絵文字（🥘, 🍔, 🥟, 🍣, 🍕）
     * 視覚的なカテゴリ表現。画面ボタンなどに使用
     */
    private String icon;
    
    /**
     * 作成日時
     * レコード作成時のタイムスタンプ
     */
    private LocalDateTime createdAt;
}
