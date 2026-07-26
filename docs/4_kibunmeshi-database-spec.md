# 4. きぶんめし DB設計書 / 키분메시 데이터베이스 설계서

> **作成日 / 작성일**: 202X-XX-XX  
> **バージョン / 버전**: 1.0  
> **対象DB / 대상DB**: MySQL 8.0

---

## 目次 / 목차

1. [ER図 / ER 다이어그램](#1-er図--er-다이어그램)
2. [テーブル一覧 / 테이블 목록](#2-テーブル一覧--테이블-목록)
3. [テーブル定義 / 테이블 정의](#3-テーブル定義--테이블-정의)
4. [インデックス定義 / 인덱스 정의](#4-インデックス定義--인덱스-정의)
5. [初期データ / 초기 데이터](#5-初期データ--초기-데이터)
6. [Gemini連携設計 / Gemini 연동 설계](#6-gemini連携設計--gemini-연동-설계)

---

## 1. ER図 / ER 다이어그램

```
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│    emotions     │       │     foods       │       │food_categories  │
├─────────────────┤       ├─────────────────┤       ├─────────────────┤
│ PK id           │◄──────┤ FK emotion_id   │       │ PK id           │
│    name         │       │ FK category_id  │──────►│    name         │
│    label        │       │    name         │       │    label        │
│    emoji        │       │    description  │       │    icon         │
│    color_code   │       │    advice_type  │       └─────────────────┘
└─────────────────┘       │    image_keyword│
                          │    is_active    │
                          └─────────────────┘
                                    │
                                    │ (N:M)
                                    ▼
                          ┌─────────────────┐
                          │  food_advice    │  ← Gemini生成用
                          ├─────────────────┤
                          │ PK id           │
                          │ FK food_id      │
                          │    template     │
                          │    tone_type    │
                          └─────────────────┘
```

---

## 2. テーブル一覧 / 테이블 목록

| No. | テーブル名 (日本語) | 테이블명 (한국어) | 説明 / 설명 | レコード数目安 |
|-----|-------------------|------------------|------------|---------------|
| 1 | 気分マスター | 감정 마스터 | 6種類の気分データ | 6件固定 |
| 2 | 料理カテゴリマスター | 음식 카테고리 마스터 | 5種類のカテゴリデータ | 5件固定 |
| 3 | 料理データ | 음식 데이터 | 感情×カテゴリの組み合わせ | 90~120件 |
| 4 | アドバイステンプレート | 조언 템플릿 | Gemini連携用テンプレート | 30~50件 |

---

## 3. テーブル定義 / 테이블 정의

### 3.1 emotions（気分マスター）

| カラム名 | データ型 | 制約 | デフォルト | 説明 |
|---------|---------|------|-----------|------|
| id | INT | PK, AUTO_INCREMENT | - | 気分ID |
| name | VARCHAR(20) | NOT NULL, UNIQUE | - | 英語名 (joy, sad, angry...) |
| label | VARCHAR(20) | NOT NULL | - | 日本語ラベル (うれしい, かなしい...) |
| emoji | VARCHAR(10) | - | NULL | 絵文字 (😊, 😢...) |
| color_code | VARCHAR(7) | - | NULL | 色コード (#FFD700...) |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 作成日時 |

**初期データ:**
```sql
INSERT INTO emotions (name, label, emoji, color_code) VALUES
('joy', 'うれしい', '😊', '#FFD700'),
('sad', 'かなしい', '😢', '#6495ED'),
('angry', 'おこ', '😠', '#DC143C'),
('tired', 'つかれた', '😴', '#708090'),
('excited', 'わくわく', '🤩', '#FF6347'),
('calm', 'おだやか', '😌', '#90EE90');
```

---

### 3.2 food_categories（料理カテゴリマスター）

| カラム名 | データ型 | 制約 | デフォルト | 説明 |
|---------|---------|------|-----------|------|
| id | INT | PK, AUTO_INCREMENT | - | カテゴリID |
| name | VARCHAR(20) | NOT NULL, UNIQUE | - | 英語名 (korean, western...) |
| label | VARCHAR(20) | NOT NULL | - | 日本語ラベル (韓国料理, 洋食...) |
| icon | VARCHAR(50) | - | NULL | アイコン絵文字 (🥘, 🍔...) |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 作成日時 |

**初期データ:**
```sql
INSERT INTO food_categories (name, label, icon) VALUES
('korean', '韓国料理', '🥘'),
('western', '洋食', '🍔'),
('chinese', '中華', '🥟'),
('japanese', '和食', '🍣'),
('others', 'その他', '🍕');
```

---

### 3.3 foods（料理データ）

| カラム名 | データ型 | 制約 | デフォルト | 説明 |
|---------|---------|------|-----------|------|
| id | INT | PK, AUTO_INCREMENT | - | 料理ID |
| name | VARCHAR(50) | NOT NULL | - | 料理名 (サムゲタン, ステーキ...) |
| emotion_id | INT | FK, NOT NULL | - | 対応気分ID |
| category_id | INT | FK, NOT NULL | - | カテゴリID |
| description | VARCHAR(200) | - | NULL | 料理説明 |
| advice_type | VARCHAR(20) | - | 'general' | アドバイスタイプ |
| image_keyword | VARCHAR(50) | - | NULL | Unsplash検索キーワード |
| is_active | BOOLEAN | - | TRUE | 有効フラグ |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 作成日時 |
| updated_at | TIMESTAMP | - | CURRENT_TIMESTAMP ON UPDATE | 更新日時 |

**インデックス:**
- `idx_emotion_category`: (emotion_id, category_id)
- `idx_is_active`: (is_active)

**初期データ例:**
```sql
-- うれしい + 韓国料理
INSERT INTO foods (name, emotion_id, category_id, description, advice_type, image_keyword) VALUES
('サムゲタン', 1, 1, '体を温める滋養強壮スープ', 'celebrate', 'samgyetang'),
('チーズタッカルビ', 1, 1, '濃厚なチーズと甘辛いチキン', 'fun', 'cheese dakgalbi'),
('ビビンバ', 1, 1, '色とりどりの具材が華やか', 'colorful', 'bibimbap');

-- かなしい + 和食
INSERT INTO foods (name, emotion_id, category_id, description, advice_type, image_keyword) VALUES
('おにぎり', 2, 4, 'シンプルな温かさ', 'gentle', 'onigiri'),
('おうどん', 2, 4, 'ほっとする一杯', 'healing', 'udon'),
('茶漬け', 2, 4, '心がホッとする味', 'comfort', 'ochazuke');

-- つかれた + 中華
INSERT INTO foods (name, emotion_id, category_id, description, advice_type, image_keyword) VALUES
('麻婆豆腐', 4, 3, 'ピリッと刺激的な味', 'spicy', 'mapo tofu'),
('チャーハン', 4, 3, 'ガツンと満足感', 'energy', 'fried rice'),
('小籠包', 4, 3, '熱々のスープが染みる', 'healing', 'xiaolongbao');

-- 各カテゴリで最低3品、合計90品程度を準備
```

---

### 3.4 food_advice_templates（アドバイステンプレート）- Gemini連携用

| カラム名 | データ型 | 制約 | デフォルト | 説明 |
|---------|---------|------|-----------|------|
| id | INT | PK, AUTO_INCREMENT | - | テンプレートID |
| emotion_id | INT | FK | - | 対応気分ID |
| advice_type | VARCHAR(20) | NOT NULL | 'general' | アドバイスタイプ |
| template_ja | VARCHAR(300) | NOT NULL | - | 日本語テンプレート |
| template_ko | VARCHAR(300) | - | NULL | 韓国語テンプレート（オプション） |
| gemini_prompt | VARCHAR(500) | - | NULL | Gemini用追加プロンプト |
| created_at | TIMESTAMP | - | CURRENT_TIMESTAMP | 作成日時 |

**初期データ例:**
```sql
-- うれしい気分用
INSERT INTO food_advice_templates (emotion_id, advice_type, template_ja, gemini_prompt) VALUES
(1, 'celebrate', '今日の達成を祝って、{food_name}でゆっくりと味わいましょう。', 
 '祝いの気持ちを入れて、ポジティブな言葉で'),
(1, 'fun', '{food_name}のワクワクする味を楽しんで、明日への元気をチャージしましょう。',
 '楽しさと元気を伝える言葉で');

-- かなしい気分用
INSERT INTO food_advice_templates (emotion_id, advice_type, template_ja, gemini_prompt) VALUES
(2, 'gentle', '小さな幸せを一つずつ。{food_name}で今日は無理せず、自分を労わってください。',
 '優しく寄り添う言葉で、無理しないことを伝えて'),
(2, 'healing', '{food_name}の温かさで心も体も温めて。明日はきっと良い日になります。',
 '癒しと希望を伝える言葉で');

-- つかれた気分用
INSERT INTO food_advice_templates (emotion_id, advice_type, template_ja, gemini_prompt) VALUES
(4, 'spicy', '辛さで疲れを吹き飛ばして。{food_name}で今日は早めに休みましょう。',
 'スパイシーさと休息の大切さを伝えて'),
(4, 'energy', '{food_name}でしっかり食べて体力回復。無理せず自分のペースで。',
 'エネルギー回復と無理しないことを伝えて');
```

---

## 4. インデックス定義 / 인덱스 정의

| インデックス名 | テーブル | カラム | 種別 | 説明 |
|--------------|---------|--------|------|------|
| PRIMARY | 全テーブル | id | 主キー | 自動採番 |
| idx_emotion_category | foods | emotion_id, category_id | 複合 | 推薦検索用 |
| idx_is_active | foods | is_active | 単一 | 有効データ検索 |
| idx_emotion_type | food_advice_templates | emotion_id, advice_type | 複合 | テンプレート検索 |

---

## 5. 初期データ / 초기 데이터

### 5.1 気分データ / 감정 데이터
```sql
INSERT INTO emotions (id, name, label, emoji, color_code) VALUES
(1, 'joy', 'うれしい', '😊', '#FFD700'),
(2, 'sad', 'かなしい', '😢', '#6495ED'),
(3, 'angry', 'おこ', '😠', '#DC143C'),
(4, 'tired', 'つかれた', '😴', '#708090'),
(5, 'excited', 'わくわく', '🤩', '#FF6347'),
(6, 'calm', 'おだやか', '😌', '#90EE90');
```

### 5.2 カテゴリデータ / 카테고리 데이터
```sql
INSERT INTO food_categories (id, name, label, icon) VALUES
(1, 'korean', '韓国料理', '🥘'),
(2, 'western', '洋食', '🍔'),
(3, 'chinese', '中華', '🥟'),
(4, 'japanese', '和食', '🍣'),
(5, 'others', 'その他', '🍕');
```

### 5.3 料理データ（サンプル）/ 음식 데이터 (샘플)
```sql
-- うれしい + 韓国料理
INSERT INTO foods (name, emotion_id, category_id, description, advice_type, image_keyword) VALUES
('サムゲタン', 1, 1, '体を温める滋養強壮スープ', 'celebrate', 'samgyetang'),
('チーズタッカルビ', 1, 1, '濃厚なチーズと甘辛いチキン', 'fun', 'cheese dakgalbi'),
('ビビンバ', 1, 1, '色とりどりの具材が華やか', 'colorful', 'bibimbap'),
('プデチゲ', 1, 1, '具材たっぷりの辛口鍋', 'exciting', 'budae jjigae');

-- かなしい + 和食
INSERT INTO foods (name, emotion_id, category_id, description, advice_type, image_keyword) VALUES
('おにぎり', 2, 4, 'シンプルな温かさ', 'gentle', 'onigiri'),
('おうどん', 2, 4, 'ほっとする一杯', 'healing', 'udon'),
('茶漬け', 2, 4, '心がホッとする味', 'comfort', 'ochazuke'),
('おでん', 2, 4, 'じっくり煮込まれた優しい味', 'warm', 'oden');

-- つかれた + 中華
INSERT INTO foods (name, emotion_id, category_id, description, advice_type, image_keyword) VALUES
('麻婆豆腐', 4, 3, 'ピリッと刺激的な味', 'spicy', 'mapo tofu'),
('チャーハン', 4, 3, 'ガツンと満足感', 'energy', 'fried rice'),
('小籠包', 4, 3, '熱々のスープが染みる', 'healing', 'xiaolongbao'),
('担々麺', 4, 3, 'コクのあるスープが体に染みる', 'comfort', 'tantanmen');

-- 各組み合わせで最低4品、合計120品程度を目安に準備
```

---

## 6. Gemini連携設計 / Gemini 연동 설계

### 6.1 連携方針 / 연동 방침

**基本方針 / 기본 방침:**
> 「料理データはDBで管理し、AIは『アドバイスの表現』を最適化する」

| 項目 | DB管理 | AI(Gemini)生成 |
|------|--------|----------------|
| 料理名 | ✅ | ❌ |
| 料理説明 | ✅ | ❌ |
| 画像キーワード | ✅ | ❌ |
| アドバイス本文 | テンプレート | ✅ 最適化 |

### 6.2 Gemini API仕様 / Gemini API 사양

**使用モデル / 사용 모델:**
- `gemini-1.5-flash` (無料版: 1分間に60リクエストまで)
- `gemini-1.5-pro` (精度重視時、有料)

**APIリクエスト例 / API 요청 예시:**
```java
// GeminiService.java
@Service
public class GeminiService {
    
    @Value("${gemini.api-key}")
    private String apiKey;
    
    public String generateAdvice(Food food, Emotion emotion, String template) {
        // Gemini API呼び出し
        String prompt = buildPrompt(food, emotion, template);
        
        // 実際のAPI呼び出し（Google GenAI SDK使用）
        // return callGeminiApi(prompt);
        
        // フォールバック: テンプレートそのまま返す
        return template.replace("{food_name}", food.getName());
    }
    
    private String buildPrompt(Food food, Emotion emotion, String template) {
        return String.format("""
            以下の料理と気分に合った、温かみのあるアドバイスを作成してください。
            
            【料理】: %s
            【説明】: %s
            【気分】: %s (%s)
            【テンプレート】: %s
            
            条件:
            - 30文字〜60文字程度
            - 親しみやすい口調
            - その気分に寄り添う内容
            - 料理名は{}で囲む
            
            出力:
            """, 
            food.getName(),
            food.getDescription(),
            emotion.getLabel(),
            emotion.getName(),
            template
        );
    }
}
```

### 6.3 エラーハンドリング / 에러 핸들링

| シナリオ | 対応 |
|---------|------|
| APIリクエスト制限超過 | テンプレートそのまま使用 |
| APIタイムアウト | テンプレートそのまま使用 |
| APIエラー | テンプレートそのまま使用 |
| レスポンス形式不正 | テンプレートそのまま使用 |

**方針**: Gemini APIは「おまけ」機能とし、必須ではない。API失敗時はDBのテンプレートをそのまま使用。

---

## 付録A. 初期データ投入スクリプト / 附录A. 초기 데이터 투입 스크립트

```sql
-- schema.sql
-- このファイルはSpring Boot起動時に自動実行される

-- emotionsテーブル
CREATE TABLE IF NOT EXISTS emotions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE,
    label VARCHAR(20) NOT NULL,
    emoji VARCHAR(10),
    color_code VARCHAR(7),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- food_categoriesテーブル
CREATE TABLE IF NOT EXISTS food_categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE,
    label VARCHAR(20) NOT NULL,
    icon VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- foodsテーブル
CREATE TABLE IF NOT EXISTS foods (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    emotion_id INT NOT NULL,
    category_id INT NOT NULL,
    description VARCHAR(200),
    advice_type VARCHAR(20) DEFAULT 'general',
    image_keyword VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (emotion_id) REFERENCES emotions(id),
    FOREIGN KEY (category_id) REFERENCES food_categories(id)
);

-- インデックス
CREATE INDEX idx_emotion_category ON foods(emotion_id, category_id);
CREATE INDEX idx_is_active ON foods(is_active);
```

---

*ドキュメント終了 / 문서 종료*
