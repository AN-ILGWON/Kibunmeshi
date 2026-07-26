-- ============================================================
-- きぶんめし (Kibunmeshi) 初期データ (PostgreSQL)
-- MySQLダンプから変換
-- ============================================================

-- クリーンアップ (users テーブルは保持)
TRUNCATE TABLE recommendation_histories CASCADE;
TRUNCATE TABLE foods CASCADE;
TRUNCATE TABLE food_categories CASCADE;
TRUNCATE TABLE food_advice_templates CASCADE;
TRUNCATE TABLE emotions CASCADE;

-- ============================================================
-- 1. emotions
-- ============================================================
INSERT INTO emotions (id, name, label, emoji, color_code, created_at) VALUES
(1,'joy','うれしい','😊','#FFD700','2026-07-25 15:18:01'),
(2,'sad','かなしい','😢','#6495ED','2026-07-25 15:18:01'),
(3,'angry','おこ','😠','#DC143C','2026-07-25 15:18:01'),
(4,'tired','つかれた','😴','#708090','2026-07-25 15:18:01'),
(5,'excited','わくわく','🤩','#FF6347','2026-07-25 15:18:01'),
(6,'calm','おだやか','😌','#90EE90','2026-07-25 15:18:01');

-- ============================================================
-- 2. food_categories
-- ============================================================
INSERT INTO food_categories (id, name, label, icon, created_at) VALUES
(1,'korean','韓国料理','🥘','2026-07-25 15:18:01'),
(2,'western','洋食','🍔','2026-07-25 15:18:01'),
(3,'chinese','中華','🥟','2026-07-25 15:18:01'),
(4,'japanese','和食','🍣','2026-07-25 15:18:01'),
(5,'indian','インド料理','🍛','2026-07-25 15:18:01'),
(6,'others','その他','🍜','2026-07-25 15:18:01');

-- ============================================================
-- 3. foods (74件 - MySQLダンプから変換、カスタム画像URL含む)
-- ============================================================
INSERT INTO foods (id, name, emotion_id, category_id, description, effect, rarity, advice_type, image_keyword, image_url, is_active, created_at, updated_at) VALUES
(1,'サムゲタン',1,1,'鶏の中に高麗人参やもち米を詰めてじっくり煮込んだ滋養強壮スープ。お祝いや嬉しい日にぴったり。','高麗人参のサポニン成分が疲労回復を助け、免疫力を高めます。','rare','celebrate','samgyetang','https://images.unsplash.com/photo-1547592166-23ac45744acd?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(2,'チーズタッカルビ',1,1,'甘辛く炒めた鶏肉と野菜に濃厚トロトロのチーズを絡めた華やかフュージョン料理。','タンパク質とカルシウム가 豊富で、エネルギー補給に最適です。','common','fun','cheese dakgalbi','https://images.unsplash.com/photo-1567620832903-9fc6debc209f?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(3,'ビビンバ',1,1,'彩り豊かなナムルと牛肉、卵を混ぜて食べる韓国の代表的なご飯料理。','ビタミンと食物繊維がバランスよく摂取できます。','sr','colorful','bibimbap','https://images.unsplash.com/photo-1553163147-622ab57be1c7?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(4,'マルゲリータピザ',1,2,'とろーりチーズと爽やかなトマトソース、バジルが広がる本格イタリアンピザ。','リコピンの抗酸化作用とチーズのタンパク質で元気をチャージ。','common','celebrate','pizza','https://images.unsplash.com/photo-1513104890138-7c749659a591?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(5,'極上サーロインステーキ',1,2,'ジューシーな肉汁が溢れる贅沢なステーキ。特別な日の幸せを彩ります。','豊富な鉄分とアミノ酸が体力をアップさせます。','ur','celebrate','steak','https://images.unsplash.com/photo-1600891964092-4316c288032e?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(6,'小籠包・点心セット',1,3,'あつあつの肉汁が溢れ出す小籠包と蒸したて点心の贅沢セット。','コラーゲンと旨味成分が心と体を満たします。','rare','fun','dim sum','https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(7,'プリプリエビチリ',1,3,'甘辛いチリソースが絡んだプリプリ食感の海老炒め。','海老のタウリンが疲労感を軽減し気分を高揚させます。','common','cheerful','ebichiri','https://images.unsplash.com/photo-1565680018434-b513d5e5fd47?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(8,'特上寿司盛り合わせ',1,4,'新鮮なマグロやウニ、サーモンが彩る日本の伝統的なお祝い寿司。','DHAとEPAが脳をリフレッシュさせます。','ur','celebrate','sushi','https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(9,'揚げたて天ぷら',1,4,'海老や季節の野菜をサクサクの衣で揚げた上品な和食。','良質なタンパク質と野菜のビタミンが摂取できます。','rare','colorful','tempura','https://images.unsplash.com/photo-1615361200141-f45040f367be?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(10,'特製ビリヤニ',1,5,'スパイスとチキンが芳醇に香るインドの高級炊き込みご飯。','スパイスの香りが脳を刺激し幸福感を高めます。','rare','celebrate','biryani','https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(11,'バターチキンカレー',1,5,'バターとトマトのまろやかなコクが広がる濃厚インドカレー。','カシューナッツとスパイスがエネルギーを高めます。','common','fun','butter chicken','https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(12,'海鮮フォー',1,6,'澄んだスープにハーブと海鮮の旨味が溶け込んだベトナム風米粉麺。','ハーブの香りが気分をすっきり高揚させます。','common','cheerful','pho','https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(13,'バインミー',1,6,'サクサクのフランスパンに肉とパクチー、なますを挟んだベトナムサンドイッチ。','炭水化物とフレッシュ野菜でエネルギーが満たされます。','common','fun','banh mi','https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(14,'キンパ',2,1,'ごま油が香る優しい味の韓国風のり巻き。ひとくちサイズで食べやすく心を癒します。','海苔のミネラルとご飯の炭水化物が心を落ち着かせます。','common','gentle','gimbap','https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(15,'ソルロンタン',2,1,'牛骨をじっくり煮込んだ乳白色の優しいスープ。胃と心にじんわり染み渡ります。','コラーゲンとアミノ酸が弱った体を優しくケアします。','rare','healing','seollongtang','https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(16,'濃厚クリームシチュー',2,2,'チキンとゴロゴロ野菜が入った温かいほっとするシチュー。','カルシウムと温かいスープが心をリラックスさせます。','common','comfort','cream stew','https://images.unsplash.com/photo-1547592180-85f173990554?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(17,'コーンポタージュスープ',2,2,'とうもろこしの自然な甘みがやさしいクリーミーなスープ。','優しい甘みが落ち込んだ気分をやさしく包みます。','common','healing','corn soup','https://images.unsplash.com/photo-1476718406336-bb5a9690ee2a?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(18,'やさしいワンタンスープ',2,3,'つるりとしたワンタンと澄んだ上湯スープが心を落ち着かせる一品。','タンパク質と温かいスープが心身をじんわり温めます。','common','healing','wonton soup','https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(19,'中華風ピータン粥',2,3,'生姜と鶏だしの効いた体にやさしい本格お粥。','消化に良く、落ち込んだ胃腸にやさしく寄り添います。','common','comfort','congee','https://images.unsplash.com/photo-1512058564366-18510be2db19?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(20,'おにぎり',2,4,'鮭や梅が入った温かい日本のソウルフード。シンプルで懐かしい味。','炭水化物が脳にセロトニンを分泌させ心を安定させます。','common','gentle','onigiri','https://images.unsplash.com/photo-1618841557871-b4664f4f2323?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(21,'出汁香るうどん',2,4,'関西風のやさしいお出汁ともちもち麺が温かい一杯。','温かい出汁が体を芯から温めて安らぎを与えます。','common','healing','udon','https://retailguide.tokubai.co.jp/wp-content/uploads/2023/11/odashi1.jpeg',TRUE,'2026-07-25 15:18:01','2026-07-25 19:55:35'),
(22,'鮭お茶漬け',2,4,'香ばしい鮭とお茶の香りでサラッと食べられる優しい一品。','カテキンと暖かい出汁で気分を落ち着かせます。','common','comfort','ochazuke','https://images.unsplash.com/photo-1623341214825-9f4f963727da?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(23,'ダール（レンズ豆）スープ',2,5,'マイルドなスパイスとレンズ豆の優しい味わいスープ。','豆のタンパク質と繊維が心を穏やかに整えます。','common','gentle','dal','https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(24,'全粒粉チャパティ',2,5,'素朴で優しい味わいのインド風薄焼きパン。','全粒粉のビタミンB群が疲れた神経をリラックスさせます。','common','comfort','chapati','https://images.unsplash.com/photo-1586201375761-83865001e31c?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(25,'やさしいクスクス',2,6,'柔らかく蒸した粒状パスタと温かい野菜スープ。','消化にやさしく、ほっとする安らぎを与えます。','common','gentle','couscous','https://images.unsplash.com/photo-1541518763669-27fef04b14da?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(26,'ハルーミチーズサラダ',2,6,'香ばしく焼いたハルーミチーズと新鮮野菜の温かみのあるサラダ。','カルシウムとビタミンが沈んだ気持ちをリフレッシュ。','rare','comfort','halloumi salad','https://images.unsplash.com/photo-1540420773420-3366772f4999?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(27,'辛口トッポッキ',3,1,'コチュジャンの激辛ソースが絡んだもちもちの韓国トッポッキ。モヤモヤを吹き飛ばす！','カプサイシンが発汗を促し、ストレスを発散させます。','common','spicy','tteokbokki','https://images.unsplash.com/photo-1590301157890-4810ed352733?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(28,'サムギョプサル',3,1,'厚切り豚バラ肉を香ばしく焼いてキムチとサンチュで包んで食べるスタミナ肉料理。','ビタミンB1とタンパク質がイライラを解消します。','rare','refresh','samgyeopsal','https://images.unsplash.com/photo-1600891964599-f61ba0e24092?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(29,'スパイシーハラペーニョバーガー',3,2,'青唐辛子の刺激と厚切りビーフパティがガツンと効いた激辛ハンバーガー。','刺激的な辛さがモヤモヤした気分をすっきりリセット！','common','refresh','spicy burger','https://images.unsplash.com/photo-1550547660-d9450f859349?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(30,'黒胡椒ペッパーステーキ',3,2,'粗挽きブラックペッパーを効かせたスパイシーなステーキ。','ペッパーのピリッとした刺激が気分転換に最適です。','rare','reset','pepper steak','https://images.unsplash.com/photo-1558030006-450675393462?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(31,'四川シビ辛麻婆豆腐',3,3,'花椒（ホアジャオ）の痺れる辛さと唐辛子がクセになる本格四川麻婆豆腐。','花椒のサンショオール成分が脳をシャキッとリフレッシュ。','rare','spicy','mapo tofu','https://images.unsplash.com/photo-1541696432-82c6da8ce7bf?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(32,'黒酢の酢豚',3,3,'コクのある黒酢の甘酸っぱさがイライラをスッキリ流してくれる極上品。','クエン酸が疲労物質の乳酸を分解し気持ちを切り替えます。','common','reset','kurozu subuta','https://images.unsplash.com/photo-1563245372-f21724e3856d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(33,'激辛オロチョンラーメン',3,4,'真っ赤な辛味噌スープとニンニクが効いた刺激的なラーメン。','唐辛子の刺激でモヤモヤをスッキリ発散できます。','common','spicy','spicy ramen','https://images.unsplash.com/photo-1569718212165-5a8e3e8c63ea?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(34,'わさびステーキ丼',3,4,'ツーンと鼻に抜ける本わさびと香ばしいステーキの絶妙な丼。','わさびのアリルイソチオシアネートが頭をスッキリさせます。','rare','reset','wasabi steak','https://images.unsplash.com/photo-1546833998-877b37c2e5c6?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(35,'ポークヴィンドゥルー',3,5,'ゴア地方発祥の激辛＆甘酸っぱいインドカレー。刺激的な味わい。','スパイスと酸味がストレスを爽快に吹き飛ばします。','rare','spicy','vindaloo','https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(36,'スパイシーサモサ',3,5,'パリッとした皮の中にスパイスの効いたジャガイモが入った揚げ点心。','スパイスの刺激で気分を爽快にリセットできます。','common','reset','samosa','https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(37,'ファラフェル',3,6,'ひよこ豆と香辛料を練り込んで揚げたスパイシーな中東のコロッケ。','豆のタンパク質とスパイス가 イライラを解消します。','common','reset','falafel','https://images.unsplash.com/photo-1593001874117-c99c800e3e7a?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(38,'スパイシーフムスディップ',3,6,'ピリ辛のチリオイルを垂らしたひよこ豆ディップ。','植物性タンパク質と辛みで気分転換できます。','common','refresh','hummus','https://images.unsplash.com/photo-1577968897966-3d4325b36b61?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(39,'キムチチゲ',4,1,'熟成キムチと豚肉、豆腐をぐつぐつ煮込んだ韓国の代表的なスタミナ鍋。','発酵食品の乳酸菌と豚肉のビタミンB1で疲労回復！','common','energy','kimchi stew','https://image.8dogam.com/resized/product/asset/v1/upload/1921662c9a554570b7c3ec72782a3709.jpg?type=big&res=3x&ext=jpg',TRUE,'2026-07-25 15:18:01','2026-07-25 19:55:35'),
(40,'水冷麺',4,1,'シャリシャリスープに冷たい麺がからむ、夏バテや疲れた体に染みる冷麺。','冷たいスープが体をリフレッシュし食欲を回復させます。','common','refresh','naengmyeon','https://images.unsplash.com/photo-1617093727343-374698b1b08d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(41,'ガーリックバターチキン',4,2,'ニンニクの香ばしい香りとバターのコクが食欲をそそるチキンソテー。','アリシンがビタミンB1の吸収を高め疲労を撃退！','common','energy','garlic chicken','https://images.unsplash.com/photo-1532550907401-a500c9a57435?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(42,'肉厚ハンバーグステーキ',4,2,'ジューシーな肉汁たっぷりのデミグラスハンバーグ。スタミナ満点！','タンパク質と鉄分が疲れた体へ素早く元気を補給します。','rare','energy','hamburg','https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(43,'パラパラ五目炒飯',4,3,'強火で一気に炒めた具だくさんの香ばしいチャーハン。','炭水化物と卵のタンパク質で素早く体力回復できます。','common','energy','fried rice','https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(44,'濃厚ごま担々麺',4,3,'香ばしい練りごまとピリ辛ラー油がコク深い滋養たっぷりのラーメン。','ごまのセサミンが抗酸化作用を発揮し疲労を癒します。','common','comfort','tantanmen','https://images.unsplash.com/photo-1612929633738-8fe44f7ec841?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(45,'特選 鰻重',4,4,'香ばしいタレで焼き上げたスタミナの王様・うなぎの蒲焼き。','ビタミンA・B群が豊富で疲労回復の最高峰です。','ur','energy','unagi','https://images.unsplash.com/photo-1580822184713-fc5400e7fe10?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(46,'具だくさん豚汁定食',4,4,'根菜と豚肉の旨味がぎゅっと詰まった体にやさしい大満足の豚汁。','豚肉と野菜の栄養バランスが疲れた体を優しく癒します。','common','healing','tonjiru','https://images.unsplash.com/photo-1604152135912-04a022e23696?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(47,'タンドリーチキン',4,5,'ヨーグルトとスパイスで漬け込み香ばしく焼いた窯焼きチキン。','スパイスと良質タンパク質が代謝を向上させます。','rare','energy','tandoori chicken','https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(48,'キーマカレー＆ナン',4,5,'ひき肉の旨味が詰まったスパイシーなカレーと焼きたてナン。','ひき肉の鉄分とスパイスがスタミナを回復させます。','common','energy','keema curry','https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(49,'ブンチャー（ベトナムつけ麺）',4,6,'炭火焼き豚肉と米麺を爽やかな甘酸っぱいヌックマムタレで頂くつけ麺。','豚肉のスタミナとさっぱりタレで疲れが吹き飛びます。','rare','refresh','bun cha','https://images.unsplash.com/photo-1609501676725-7186f017a4b7?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(50,'フレッシュ生春巻き',4,6,'エビや海鮮、たっぷりのハーブをライスペーパーで巻いたヘルシー料理。','ビタミンとミネラルが疲れた体に染み渡ります。','common','healing','goi cuon','https://images.unsplash.com/photo-1534422298391-e4f8c172dddb?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(51,'プデチゲ',5,1,'ソーセージ、スパム、ラーメン、チーズが入ったテンションの上がる賑やか旨辛鍋。','多彩な具材のエネルギーで気持ちがさらに高揚します！','common','exciting','budae jjigae','https://images.unsplash.com/photo-1543339308-43e59d6b73a6?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(52,'ヤンニョムチキン',5,1,'甘辛いタレとサクサクの衣がやみつきになる韓国風フライドチキン。','良質なタンパク質と美味しい刺激でワクワク感最高潮！','rare','cheerful','yangnyeom chicken','https://images.unsplash.com/photo-1562967914-608f82629710?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(53,'ふわとろドレスオムライス',5,2,'トロトロの半熟卵と濃厚なデミグラスソースが美しい洋食屋さんのオムライス。','卵のレシチンが脳の活性化を促し気持ちを高めます。','common','fun','omurice','https://images.unsplash.com/photo-1525351484163-7529414344d8?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(54,'サクサクジャンボエビフライ',5,2,'大きな海老をサクサクに揚げて自家製タルタルソースで食べる楽しい洋食。','海老のDHAと歯ごたえがワクワク感を倍増させます。','common','cheerful','ebi fry','https://images.unsplash.com/photo-1618040996337-56904b7850b9?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(55,'パリパリ焼き餃子',5,3,'羽つきのパリッとした皮から肉汁がジュワッと広がる中華の王道。','旨味成分のグルタミン酸が心をワクワク楽しくさせます。','common','exciting','dumplings','https://images.unsplash.com/photo-1563245372-f21724e3856d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(56,'五目サクサク春巻き',5,3,'とろりとした具材をサクサクの皮で包んだ見た目も美味しい春巻き。','食感の楽しさがテンションをアップさせます。','common','fun','spring rolls','https://images.unsplash.com/photo-1544025162-d76694265947?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(57,'豪華海鮮丼',5,4,'マグロ、イクラ、ホタテがキラキラ輝く豪華な海の幸盛り合わせ丼。','魚介のオメガ3脂肪酸が心を前向きにワクワクさせます。','ur','exciting','kaisendon','https://images.unsplash.com/photo-1617196034183-421b4917c92d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(58,'黒毛和牛すき焼き',5,4,'柔らかい和牛を甘辛い割下と生卵で頂く贅沢で華やかな鍋。','極上の肉の旨味が特別なワクワク感をもたらします。','sr','cheerful','sukiyaki','https://images.unsplash.com/photo-1618841556834-e86f7e93e13d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(59,'トロピカルマンゴーラッシー',5,5,'濃厚なマンゴーのフルーティーな甘みとヨーグルトが爽やかなデザートドリンク。','マンゴーのビタミンCと甘みがワクワク気分を高めます。','common','fun','mango lassi','https://images.unsplash.com/photo-1551024709-8f23befc6f87?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(60,'パニールティッカ',5,5,'インドのチーズと野菜をスパイスで香ばしく焼いた鮮やかな料理。','チーズの豊富なタンパク質が活力を与えます。','common','cheerful','paneer tikka','https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(61,'チキンケバブサンド',5,6,'回転焼きチキンとシャキシャキ野菜、スパイシーソースの賑やかサンド。','スパイスとボリュームでテンションがさらにアップ！','rare','exciting','kebab','https://images.unsplash.com/photo-1529006557810-274b9b2fc783?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(62,'ベトナム風お好み焼き・バインセオ',5,6,'ココナッツ香るサクサク生地でエビやもやしを包んだ鮮やかなお料理。','食感と香りの楽しさがワクワク感を演出します。','common','fun','banh xeo','https://images.unsplash.com/photo-1618040996342-29925f3fa369?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(63,'やさしい野菜粥（ヤチェジュク）',6,1,'みじん切り野菜とごま油がやさしく香る韓国の伝統お粥。','消化に優しく、疲れた胃腸と心を静かに整えます。','common','simple','juk','https://images.unsplash.com/photo-1608039755401-742074f0548d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(64,'ナムル盛り合わせ',6,1,'ほうれん草、モヤシ、人参を優しい味付けで和えたヘルシー料理。','野菜のビタミンとミネラルが心身を穏やかに保ちます。','common','fresh','namul','https://images.unsplash.com/photo-1586201375761-83865001e31c?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(65,'トマトとバジルのパスタ',6,2,'フレッシュトマトとフレッシュバジルのシンプルで味わい深いパスタ。','炭水化物がセロトニン分泌を促し穏やかな気分に導きます。','common','simple','pasta','https://zenb.jp/cdn/shop/articles/1212_dfe9b093-330a-4461-b63a-0ba9ff34c938_645x.jpg?v=1774940872',TRUE,'2026-07-25 15:18:01','2026-07-25 19:55:35'),
(66,'アボカドとフレッシュグリーンサラダ',6,2,'クリーミーなアボカドと旬の野菜をシンプルなオリーブオイルで。','良質な脂質と食物繊維が心と体をやさしくリフレッシュ。','common','fresh','salad','https://images.unsplash.com/photo-1490645935967-10de6ba17061?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(67,'なめらか杏仁豆腐',6,3,'つるんと滑らかな食感と杏仁の優しい香りが広がる中華スイーツ。','ほのかな甘みが緊張をほぐし穏やかな時間を演出します。','common','simple','annin tofu','https://images.unsplash.com/photo-1488477181946-6428a0291777?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(68,'シャキシャキ野菜炒め',6,3,'素材の味を生かした薄味の中華風野菜炒め。','豊富なビタミンが体の調子を穏やかに整えます。','common','fresh','vegetable stir fry','https://images.unsplash.com/photo-1540420773420-3366772f4999?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(69,'出汁香る茶碗蒸し',6,4,'銀杏やエビが入った、ぷるんと柔らかく優しい和風蒸し料理。','優しいお出汁と卵のタンパク質が心を落ち着かせます。','common','simple','chawanmushi','https://images.unsplash.com/photo-1617093727343-374698b1b08d?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(70,'豆腐とワカメのおみそ汁',6,4,'お出汁と味噌の香りが胸にじんわり広がる日本の基本の味。','味噌の発酵パワーが腸内環境と心を穏やかに整えます。','common','warm','miso soup','https://images.unsplash.com/photo-1604152135912-04a022e23696?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(71,'マイルド豆カレー（キチュリ）',6,5,'米と豆をマイルドなスパイスで優しく煮込んだインドのお粥風料理。','優しいスパイスが消化を助け心をリラックスさせます。','common','simple','khichdi','https://images.unsplash.com/photo-1586201375761-83865001e31c?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(72,'ホットマサラチャイ',6,5,'シナモンやカルダモンがほのかに香る温かい煮出しミルクティー。','ハーブの香りと温かいミルクがリラックス効果を発揮します。','common','warm','chai','https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(73,'やさしい野菜クスクス',6,6,'柔らかい野菜とズッキーニがのったマイルドなアフリカ料理。','素朴で優しい味わいが心を落ち着かせてくれます。','common','simple','couscous','https://images.unsplash.com/photo-1541518763669-27fef04b14da?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01'),
(74,'フルーツグラノーラ＆ミルク',6,6,'オーツ麦とドライフルーツに冷たいミルクをかけた軽やかな朝食。','ビタミンB群が静かなエネルギー代謝をサポートします。','ur','light','cereal','https://images.unsplash.com/photo-1517673400267-0251440c45dc?w=600&auto=format&fit=crop',TRUE,'2026-07-25 15:18:01','2026-07-25 15:18:01');

-- ============================================================
-- 4. food_advice_templates
-- ============================================================
INSERT INTO food_advice_templates (id, emotion_id, advice_type, template_ja, template_ko, gemini_prompt, is_active, created_at) VALUES
(1,1,'celebrate','おめでとう！{food_name}で今日の喜びをさらに盛り上げましょう。','축하해요! {food_name}으로 오늘의 기쁨을 더 높여봐요.',NULL,TRUE,'2026-07-25 15:18:01'),
(2,1,'fun','楽しい気分には{food_name}がぴったり！美味しく食べて笑顔を増やそう。','즐거운 기분에는 {food_name}이 딱이에요! 맛있게 먹고 웃음을 늘려봐요.',NULL,TRUE,'2026-07-25 15:18:01'),
(3,1,'colorful','カラフルな気分には{food_name}！見た目も味も楽しんで。','화려한 기분에는 {food_name}! 보기에도 맛에도 즐겨보세요.',NULL,TRUE,'2026-07-25 15:18:01'),
(4,2,'gentle','心が温まる{food_name}で優しく包み込まれて。','마음이 따뜻해지는 {food_name}으로 부드럽게 감싸져요.',NULL,TRUE,'2026-07-25 15:18:01'),
(5,2,'healing','癒しの{food_name}で心を休めましょう。','치유의 {food_name}으로 마음을 쉬게 해요.',NULL,TRUE,'2026-07-25 15:18:01'),
(6,2,'comfort','優しい味の{food_name}が心に寄り添います。','부드러운 맛의 {food_name}이 마음에 다가와요.',NULL,TRUE,'2026-07-25 15:18:01'),
(7,2,'warm','温かい{food_name}で心を温めて。','따뜻한 {food_name}으로 마음을 데워요.',NULL,TRUE,'2026-07-25 15:18:01'),
(8,3,'spicy','刺激的な{food_name}でモヤモヤを吹き飛ばそう！','자극적인 {food_name}으로 스트레스 발산!',NULL,TRUE,'2026-07-25 15:18:01'),
(9,3,'refresh','スッキリした味の{food_name}で気分転換！','상쾌한 taste의 {food_name}으로 기분 전환!',NULL,TRUE,'2026-07-25 15:18:01'),
(10,3,'reset','甘酸っぱい{food_name}で気持ちをリセット。','달콤신 {food_name}으로 기분을 리셋.',NULL,TRUE,'2026-07-25 15:18:01'),
(11,3,'calm','スッキリとした{food_name}で落ち着こう。','상쾌한 {food_name}으로 진정해요.',NULL,TRUE,'2026-07-25 15:18:01'),
(12,4,'spicy','ピリッと刺激的な{food_name}で目を覚まそう。','얼큰한 {food_name}으로 눈을 떠요.',NULL,TRUE,'2026-07-25 15:18:01'),
(13,4,'energy','ガツンと満足感のある{food_name}でエネルギーチャージ！','든든한 {food_name}으로 에너지 충전!',NULL,TRUE,'2026-07-25 15:18:01'),
(14,4,'healing','熱々の{food_name}で体を癒そう。','뜨끈한 {food_name}으로 몸을 치유해요.',NULL,TRUE,'2026-07-25 15:18:01'),
(15,4,'comfort','コクのある{food_name}が体に染みる。','진한 {food_name}이 몸에 배어요.',NULL,TRUE,'2026-07-25 15:18:01'),
(16,5,'exciting','ワクワクする気分には{food_name}！','설레는 기분에는 {food_name}!',NULL,TRUE,'2026-07-25 15:18:01'),
(17,5,'fun','楽しい{food_name}でさらに盛り上がろう！','재미있는 {food_name}으로 더 뜨겁게!',NULL,TRUE,'2026-07-25 15:18:01'),
(18,5,'cheerful','華やかな{food_name}でテンションアップ！','화려한 {food_name}으로 텐션업!',NULL,TRUE,'2026-07-25 15:18:01'),
(19,5,'cozy','温かみのある{food_name}で幸せな気分に。','따뜻한 {food_name}으로 행복한 기분으로.',NULL,TRUE,'2026-07-25 15:18:01'),
(20,6,'simple','シンプルな{food_name}で落ち着いた時間を。','심플한 {food_name}으로 차분한 시간을.',NULL,TRUE,'2026-07-25 15:18:01'),
(21,6,'warm','スパイスの香りが広がる{food_name}でリラックス。','스파이스 향이 퍼지는 {food_name}으로 릴랙스.',NULL,TRUE,'2026-07-25 15:18:01'),
(22,6,'fresh','新鮮な{food_name}で心も体もリフレッシュ。','신선한 {food_name}으로 마음도 몸도 리프레시.',NULL,TRUE,'2026-07-25 15:18:01'),
(23,6,'light','軽い{food_name}で胃も心も軽く。','가벼운 {food_name}으로 위도 마음도 가볍게.',NULL,TRUE,'2026-07-25 15:18:01'),
(24,NULL,'general','今日の気分に合った{food_name}を楽しんでください。','오늘의 기분에 맞는 {food_name}을 즐겨보세요.',NULL,TRUE,'2026-07-25 15:18:01');

-- ============================================================
-- 5. users (既存データがあれば無視)
-- ============================================================
INSERT INTO users (id, username, password_hash, nickname, email, role, enabled, created_at, updated_at) VALUES
(1,'aik5004','$2a$10$eoX2zzNQZUGyTKW5DfLpT.HEoUW7GZEGOAjcvxTFm4piDuRISrstm','rewq','cfdfd@gmail.com','USER',TRUE,'2026-07-25 12:31:35','2026-07-25 12:31:35'),
(2,'testuser01','$2a$10$w3jh7zqxzM7EXG7n5H9tTey3Vp8IujyUn/uv3cIvDz3T2gnHY1Qs2','テストユーザー','test01@example.com','USER',TRUE,'2026-07-25 13:21:21','2026-07-25 13:21:21'),
(3,'testuser02','$2a$10$axwdV9Py5wynlhscOn91xuNyAGLkxRvHr0GPzKHst12R9IMyltmuK','テストユーザー2','test02@example.com','USER',TRUE,'2026-07-25 13:21:58','2026-07-25 13:21:58'),
(4,'aws1234','$2a$10$nggETfIDeyauJIaxo/UQguzuxIp0TLiumYUSfJ9AmjWT1fHqaJiWG','かここ','sund@gmail.com','USER',TRUE,'2026-07-25 14:53:00','2026-07-25 14:53:00'),
(5,'qwer123','$2a$10$xpV2.AAylQN05JhQTwQRseDJVyLZslmeTzuxCA5RXpgdFcmk1OZ2O','漫湖','sw1d@gmail.com','USER',TRUE,'2026-07-25 19:48:34','2026-07-25 19:48:34'),
(6,'cal101','$2a$10$nLrrYLx0yOOYdd5WqQFHvOtw48gJNllwMRHOer86hTqH8ItoejSxO','くーりんぱ','snd@gmail.com','USER',TRUE,'2026-07-25 20:04:34','2026-07-25 20:04:34')
ON CONFLICT DO NOTHING;

-- ============================================================
-- 6. recommendation_histories
-- ============================================================
INSERT INTO recommendation_histories (id, user_id, food_id, emotion_id, category_id, is_favorite, is_public, advice_text, created_at) VALUES
(1,4,17,2,2,FALSE,TRUE,'癒しのコーンポタージュスープで心を休めましょう。','2026-07-25 15:18:53'),
(2,1,50,4,6,TRUE,TRUE,'熱々のフレッシュ生春巻きで体を癒そう。','2026-07-25 16:16:49'),
(3,1,69,6,4,FALSE,TRUE,'シンプルな出汁香る茶碗蒸しで落ち着いた時間を。','2026-07-25 16:46:55'),
(4,4,60,5,5,FALSE,TRUE,'華やかなパニールティッカでテンションアップ！','2026-07-25 17:34:16'),
(5,4,34,3,4,FALSE,FALSE,'甘酸っぱいわさびステーキ丼で気持ちをリセット。','2026-07-25 17:39:30'),
(6,4,34,3,4,FALSE,FALSE,'甘酸っぱいわさびステーキ丼で気持ちをリセット。','2026-07-25 17:39:41'),
(7,4,39,4,1,FALSE,TRUE,'ガツンと満足感のあるキムチチゲでエネルギーチャージ！','2026-07-25 17:39:56'),
(8,4,66,6,2,FALSE,FALSE,'新鮮なアボカドとフレッシュグリーンサラダで心も体もリフレッシュ。','2026-07-25 17:40:35'),
(9,4,65,6,2,FALSE,FALSE,'シンプルなトマトとバジルのパスタで落ち着いた時間を。','2026-07-25 17:40:44'),
(10,4,65,6,2,FALSE,TRUE,'シンプルなトマトとバジルのパスタで落ち着いた時間を。','2026-07-25 17:40:49'),
(11,1,16,2,2,FALSE,TRUE,'優しい味の濃厚クリームシチューが心に寄り添います。','2026-07-25 17:49:06'),
(12,1,64,6,1,FALSE,FALSE,'新鮮なナムル盛り合わせで心も体もリフレッシュ。','2026-07-25 17:52:27'),
(13,4,36,3,5,FALSE,FALSE,'甘酸っぱいスパイシーサモサで気持ちをリセット。','2026-07-25 18:44:24'),
(14,1,21,2,4,FALSE,TRUE,'癒しの出汁香るおうどんで心を休めましょう。','2026-07-25 19:15:39'),
(15,6,57,5,4,FALSE,FALSE,'ワクワクする気分には豪華海鮮丼！','2026-07-25 20:06:23'),
(16,1,39,4,1,TRUE,FALSE,'ガツンと満足感のあるキムチチゲでエネルギーチャージ！','2026-07-26 01:20:52'),
(17,4,5,1,2,FALSE,TRUE,'おめでとう！極上サーロインステーキで今日の喜びをさらに盛り上げましょう。','2026-07-26 03:05:08'),
(18,4,19,2,3,FALSE,TRUE,'優しい味の中華風ピータン粥が心に寄り添います。','2026-07-26 03:05:42'),
(19,1,65,6,2,FALSE,TRUE,'シンプルなトマトとバジルのパスタで落ち着いた時間を。','2026-07-26 04:27:19'),
(20,1,10,1,5,FALSE,TRUE,'おめでとう！特製ビリヤニで今日の喜びをさらに盛り上げましょう。','2026-07-26 04:27:42');

-- ============================================================
-- シーケンスリセット
-- ============================================================
SELECT setval('emotions_id_seq', (SELECT MAX(id) FROM emotions));
SELECT setval('food_categories_id_seq', (SELECT MAX(id) FROM food_categories));
SELECT setval('foods_id_seq', (SELECT MAX(id) FROM foods));
SELECT setval('food_advice_templates_id_seq', (SELECT MAX(id) FROM food_advice_templates));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('recommendation_histories_id_seq', (SELECT MAX(id) FROM recommendation_histories));
