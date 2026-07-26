package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 気分（Emotion）エンティティ
 * 
 * ユーザーが選択できる6種類の気分を表現するドメインモデル。
 * 😊 うれしい、😢 かなしい、😠 おこ、😴 つかれた、🤩 わくわく、😌 おだやか
 * 
 * @author 作者名
 * @version 1.0.0
 */
@Data
public class Emotion {
    
    /**
     * 気分ID（1-6）
     * 主キー、自動採番
     */
    private Integer id;
    
    /**
     * 英語名（joy, sad, angry, tired, excited, calm）
     * システム内部で使用する識別子
     */
    private String name;
    
    /**
     * 日本語ラベル（うれしい、かなしい...）
     * 画面表示用の日本語文字列
     */
    private String label;
    
    /**
     * 絵文字（😊, 😢, 😠...）
     * 視覚的な感情表現。画面ボタンなどに使用
     */
    private String emoji;
    
    /**
     * 色コード（#FFD700, #6495ED...）
     * 感情に対応したブランドカラー。
     * 背景色やボーダー色などに使用
     */
    private String colorCode;
    
    /**
     * 作成日時
     * レコード作成時のタイムスタンプ
     */
    private LocalDateTime createdAt;
    
    /**
     * 色コードのgetter（フィールド名のマッピング用）
     * MyBatisのmap-underscore-to-camel-case設定との互換性のため
     * 
     * @return 色コード文字列
     */
    public String getColorCode() {
        return colorCode;
    }
    
    /**
     * 作成日時のgetter（フィールド名のマッピング用）
     * 
     * @return 作成日時
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
