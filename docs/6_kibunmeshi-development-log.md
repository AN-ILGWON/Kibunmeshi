# 6. きぶんめし 開発日誌 / 키분메시 개발 일지

> **プロジェクト名 / 프로젝트명**: きぶんめし (Kibunmeshi)  
> **開発期間 / 개발 기간**: 2週間（14日間）/ 2주일 (14일)  
> **技術スタック / 기술 스택**: Spring Boot 3.x, Thymeleaf, MyBatis, MySQL, Google Gemini API  
> **開始日 / 시작일**: 20XX-XX-XX  
> **予定終了日 / 예정 종료일**: 20XX-XX-XX

---

## 目次 / 목차

1. [Week 1: 基盤構築と基本機能](#week-1-基盤構築と基本機能)
2. [Week 2: 機能拡張と完成](#week-2-機能拡張と完成)
3. [振り返り / 振り返り](#振り返り--振り返り)
4. [参考リンク / 参考リンク](#参考リンク--参考リンク)

---

## Week 1: 基盤構築と基本機能

### Day 1: プロジェクト初期設定
**日付 / 날짜**: 20XX-XX-XX  
**作業時間 / 작업 시간**: X時間

#### ✅ 完了した作業 / 완료한 작업
- [x] Spring Initializrでプロジェクト作成
  - Dependencies: Spring Web, Thymeleaf, MyBatis Framework, MySQL Driver, Lombok
  - Java 17, Spring Boot 3.2.x
- [x] プロジェクト構成の整理
  - package: `com.kibunmeshi`
  - controller, service, domain, repository, config
- [x] `application.yml`設定
  - DB接続情報
  - Thymeleaf設定
  - MyBatis設定
  - Gemini API設定 (オプション)

#### 📁 作成したファイル / 생성한 파일
| ファイルパス | 説明 |
|------------|------|
| `build.gradle` | 依存関係定義 |
| `src/main/resources/application.yml` | アプリケーション設定 |
| `src/main/java/com/kibunmeshi/KibunmeshiApplication.java` | メインクラス |

#### 💡 学んだこと・気づき / 배운 것・느낀 것
- 

#### ⚠️ 詰まった点・解決策 / 막힌 점・해결책
| 問題 | 原因 | 解決方法 |
|------|------|---------|
| | | |

#### 📅 次にやること / 다음에 할 일
- DB設計とテーブル作成
- schema.sql, data.sql作成

---

### Day 2: データベース設計と初期データ
**日付 / 날짜**: 20XX-XX-XX  
**作業時間 / 작업 시간**: X時間

#### ✅ 完了した作業 / 완료한 작업
- [x] `schema.sql`作成
  - emotionsテーブル
  - food_categoriesテーブル
  - foodsテーブル
  - food_advice_templatesテーブル（Gemini連携用）
- [x] `data.sql`作成
  - emotionsデータ（6件）
  - food_categoriesデータ（5件）
  - foodsデータ（サンプル12件）
  - food_advice_templatesデータ（サンプル）
- [x] Entityクラス作成
  - Emotion.java
  - FoodCategory.java
  - Food.java

#### 📁 作成したファイル / 생성한 파일
| ファイルパス | 説明 |
|------------|------|
| `src/main/resources/schema.sql` | テーブル定義 |
| `src/main/resources/data.sql` | 初期データ |
| `src/main/java/com/kibunmeshi/domain/Emotion.java` | 気分エンティティ |
| `src/main/java/com/kibunmeshi/domain/FoodCategory.java` | カテゴリエンティティ |
| `src/main/java/com/kibunmeshi/domain/Food.java` | 料理エンティティ |

#### 💡 学んだこと・気づき / 배운 것・느낀 것
- 

#### ⚠️ 詰まった点・解決策 / 막힌 점・해결책
| 問題 | 原因 | 解決方法 |
|------|------|---------|
| | | |

#### 📅 次にやること / 다음에 할 일
- MyBatisマッパー実装
- FoodMapperインターフェース・XML作成

---

### Day 3: MyBatisマッパー実装
**日付 / 날짜**: 20XX-XX-XX  
**作業時間 / 작업 시간**: X時間

#### ✅ 完了した作業 / 완료한 작업
- [x] FoodMapperインターフェース作成
  - `findByEmotionAndCategory(Integer emotionId, Integer categoryId)`
  - `findById(Integer id)`
  - `findAll()`
- [x] FoodMapper.xml作成
  - SQLクエリ定義
  - resultMap定義
- [x] テストでデータ取得確認
  - @PostConstructでテスト出力
  - ログ確認

#### 📁 作成したファイル / 생성한 파일
| ファイルパス | 説明 |
|------------|------|
| `src/main/java/com/kibunmeshi/repository/FoodMapper.java` | Mapperインターフェース |
| `src/main/resources/mapper/FoodMapper.xml` | SQL定義XML |

#### 💡 学んだこと・気づき / 배운 것・느낀 것
- 

#### ⚠️ 詰まった点・解決策 / 막힌 점・해결책
| 問題 | 原因 | 解決方法 |
|------|------|---------|
| | | |

#### 📅 次にやること / 다음에 할 일
- トップ画面（気分選択）作成
- TopController, top.html

---

### Day 4: トップ画面（気分選択）実装
**日付 / 날짜**: 20XX-XX-XX  
**作業時間 / 작업 시간**: X時間

#### ✅ 完了した作業 / 완료한 작업
- [x] TopController作成
  - `@GetMapping("/")`
  - emotionsテーブルから全件取得
- [x] top.html作成
  - ロゴ表示
  - タイトル「今日の気分は？」
  - 気分ボタン6個（絵文字＋ラベル）
  - ボタン押下で/categoryに遷移
- [x] CSSスタイリング（基本）
  - 中央寄せ
  - ボタン大きく（タップしやすく）
  - 絵文字を大きく表示

#### 📁 作成したファイル / 생성한 파일
| ファイルパス | 説明 |
|------------|------|
| `src/main/java/com/kibunmeshi/controller/TopController.java` | トップ画面コントローラー |
| `src/main/resources/templates/top.html` | トップ画面テンプレート |
| `src/main/resources/static/css/style.css` | 基本スタイルシート |

#### 💡 学んだこと・気づき / 배운 것・느낀 것
- 

#### ⚠️ 詰まった点・解決策 / 막힌 점・해결책
| 問題 | 原因 | 解決方法 |
|------|------|---------|
| | | |

#### 📅 次にやること / 다음에 할 일
- カテゴリ選択画面作成
- CategoryController, category.html

---

### Day 5: 会員登録機能強化とUI最終調整
**日付 / 날짜**: 2026-07-22  
**作業時間 / 작업 시간**: 4시간

#### ✅ 完了した作業 / 완료한 작업
- [x] 全テンプレートファイルから韓国語テキストを削除
- [x] 会員登録画面に「パスワード（確認）」フィールドを追加
- [x] パスワード確認のバリデーションロジックを実装
- [x] ニックネームの重複確認機能を実装
- [x] `application.yml`の`spring.sql.init.mode`を`never`に変更（データが消えないように）
- [x] 開発用デバッグコントローラー（`DebugController`）を追加

#### 📁 作成・変更したファイル / 생성・변경한 파일
| ファイルパス | 説明 |
|------------|------|
| `src/main/resources/templates/top.html` | 韓国語削除 |
| `src/main/resources/templates/category.html` | 韓国語削除 |
| `src/main/resources/templates/result.html` | 韓国語削除 |
| `src/main/resources/templates/mypage.html` | 韓国語削除 |
| `src/main/resources/templates/login.html` | 韓国語削除 |
| `src/main/resources/templates/signup.html` | 韓国語削除 + パスワード確認フィールド追加 |
| `src/main/resources/templates/result-empty.html` | 韓国語削除 |
| `src/main/java/com/kibunmeshi/repository/UserMapper.java` | `countByNickname()`, `findAll()`追加 |
| `src/main/resources/mapper/UserMapper.xml` | `countByNickname`, `findAll`クエリ追加 |
| `src/main/java/com/kibunmeshi/service/UserService.java` | `isNicknameAvailable()`追加 |
| `src/main/java/com/kibunmeshi/controller/AuthController.java` | パスワード確認ロジック追加 |
| `src/main/resources/application.yml` | `spring.sql.init.mode=never`に変更 |
| `src/main/java/com/kibunmeshi/controller/DebugController.java` | 新規作成（開発用） |

#### 💡 学んだこと・気づき / 배운 것・느낀 것
- Spring Securityの`anyRequest().permitAll()`で開発用エンドポイントもアクセス可能になる
- `sql.init.mode=never`にすることで、アプリ再起動時にデータが初期化されない
- パスワード確認はクライアントサイドとサーバーサイド両方でチェックするのが望ましい

#### ⚠️ 詰まった点・解決策 / 막힌 점・해決책
| 問題 | 原因 | 解決方法 |
|------|------|---------|
| ポート8080が使用中で起動できない | 別のアプリが8080を使用中 | `application.yml`でサーバーポートを8081に変更 |
| アプリ再起動時にユーザーデータが消える | `spring.sql.init.mode=always`だった | `never`に変更 |

#### 📅 次にやること / 다음에 할 일
- 必要に応じてDebugControllerを削除
- 追加機能（プロフィール編集、カード削除など）を実装（任意）

---

## Week 2: 機能拡張と完成

(続く...)

---

## 振り返り / 振り返り

### 良かった点 / 좋았던 점
- 

### 改善点 / 개선할 점
- 

### 学んだこと / 배운 것
- 

### 次に活かせるポイント / 다음에 활용할 포인트
- 

---

## 参考リンク / 参考リンク

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MyBatis Documentation](https://mybatis.org/mybatis-3/)
- [Google Gemini API Documentation](https://ai.google.dev/)
- [Unsplash API](https://unsplash.com/developers)

---

*ドキュメント終了 / 문서 종료*
