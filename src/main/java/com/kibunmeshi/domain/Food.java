package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 料理（Food）エンティティ
 * 
 * ユーザーに推薦される料理の情報を表現するドメインモデル。
 * 気分とカテゴリの組み合わせに基づいて、適切な料理を検索・推薦する。
 * 
 * @author 作者名
 * @version 1.0.0
 */
@Data
public class Food {
    
    /**
     * 料理ID
     * 主キー、自動採番
     */
    private Integer id;
    
    /**
     * 料理名（日本語）
     * 例：サムゲタン、おにぎり、麻婆豆腐など
     */
    private String name;
    
    /**
     * 対応気分ID（外部キー）
     * emotionsテーブルへの参照
     */
    private Integer emotionId;
    
    /**
     * カテゴリID（外部キー）
     * food_categoriesテーブルへの参照
     */
    private Integer categoryId;
    
    /**
     * 料理説明
     * 料理の特徴や材料などの説明文
     */
    private String description;
    
    /**
     * 栄養成分・効果
     * AI推薦時に生成される、料理の栄養成分と効果の説明
     */
    private String effect;

    /**
     * カードレアリティ
     * common, rare, sr, ur
     */
    private String rarity;
    
    /**
     * アドバイスタイプ
     * アドバイステンプレートを選択するための分類
     * celebrate, gentle, spicy, exciting など
     */
    private String adviceType;
    
    /**
     * Unsplash検索キーワード
     * 料理画像を検索するための英語キーワード
     * 例：samgyetang, onigiri, mapo tofu
     */
    private String imageKeyword;

    /**
     * 料理画像URL
     * AI推薦時に画像検索サービスから取得した画像URL
     */
    private String imageUrl;
    
    /**
     * 有効フラグ
     * true: 推薦対象 / false: 推薦対象外
     */
    private Boolean isActive;
    
    /**
     * 作成日時
     * レコード作成時のタイムスタンプ
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新日時
     * レコード更新時のタイムスタンプ（自動更新）
     */
    private LocalDateTime updatedAt;
    
    // ===== 関連エンティティ（JOIN時に使用） =====
    
    /**
     * 対応気分エンティティ
     * MyBatisの結果マッピング時に自動設定
     */
    private Emotion emotion;
    
    /**
     * カテゴリエンティティ
     * MyBatisの結果マッピング時に自動設定
     */
    private FoodCategory category;
    
    // ===== ビジネスロジックメソッド =====
    
    /**
     * Unsplash画像URLを生成する
     * 優先順位:
     * 1. imageUrl (DBに直接保存されたUnsplash photo直URL)
     * 2. imageKeywordがhttp(s)で始まる場合 → 直接URLとして使用
     * 3. imageKeywordが通常キーワードの場合 → Unsplash Source URL(キャッシュバスター付)
     * 4. 上記全部なし → デフォルト定食画像
     *
     * @return Unsplash画像のURL文字列
     */
    public String getUnsplashImageUrl() {
        // 1. 明示的に保存された画像URL を最優先
        if (imageUrl != null && !imageUrl.isEmpty() && !imageUrl.isBlank()) {
            return imageUrl;
        }

        // 2. imageKeywordが直URL形式の場合
        if (imageKeyword != null && !imageKeyword.isEmpty() &&
            (imageKeyword.startsWith("http://") || imageKeyword.startsWith("https://"))) {
            return imageKeyword;
        }

        // 3. imageKeywordを使ってUnsplash Source URL を生成 (キャッシュ回避のため nameのhashを追加)
        if (imageKeyword != null && !imageKeyword.isEmpty()) {
            String keyword = imageKeyword.trim().replaceAll("\\s+", "+");
            String cacheBuster = String.valueOf(Math.abs((name == null ? keyword : name).hashCode() % 100000));
            return "https://source.unsplash.com/600x400/?" + keyword + "&sig=" + cacheBuster;
        }

        // 4. デフォルトフォールバック画像
        return "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=600&auto=format&fit=crop";
    }
    
    /**
     * アドバイステンプレートに料理名を埋め込む
     * 
     * @param template アドバイステンプレート（{food_name}を含む）
     * @return 料理名が埋め込まれたアドバイス文
     */
    public String generateAdvice(String template) {
        if (template == null || template.isEmpty()) {
            return name + "を楽しんでください。";
        }
        return template.replace("{food_name}", name)
                        .replace("【料理名】", name);
    }
    
    /**
     * 料理の概要を文字列で返す
     * 
     * @return 料理情報の文字列表現
     */
    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emotionId=" + emotionId +
                ", categoryId=" + categoryId +
                ", adviceType='" + adviceType + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
