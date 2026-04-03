<!--
ソース名: README.md
Lang: Markdown
Function: 教材向けの起動手順・sideload 手順・使うマニフェストの明示
Note: dokoTsubu Java プロジェクトとは独立。Id（GUID）は他アドインと重複させないこと。
Author:Takashi Oikawa
Date:2026/04/04
LastUp:2026/04/04
-->

# どこつぶ（デモ）— Excel Office Add-in 教材用

説明用モックです。認証・DB・API・シート操作は含みません。

## 使い分けるマニフェスト

| ファイル | 用途 |
|----------|------|
| **manifest.local.xml** | **Mac／授業でのローカル検証**（`http://localhost:3000`） |
| manifest.xml | `manifest.local.xml` と同一内容（ツールが `manifest.xml` 名のみ想定する場合用） |
| manifest.prod.sample.xml | HTTPS 配信の雛形。`YOUR-HOST.example` と `Id` を自前に差し替え |

**Id（GUID）** はアドインごとに一意です。別プロジェクトのマニフェストと同じ値にしないでください。

## 前提

- Python 3（`python3 -m http.server` が使えること）
- Excel for Mac（Microsoft 365 など）
- 初回のみネットワーク（Office.js・教材用アイコン URL の取得）

## ローカルサーバーの起動

このフォルダを **ドキュメントルート** にし、**ポート 3000** で起動します（`manifest.local.xml` と一致）。

**ターミナル:**

```bash
cd office-addin-dokotsubu-demo
bash start-local.sh
```

実行属性を付ける場合: `chmod +x start-local.sh start-local.command` のうえで `./start-local.sh` でも可。

**Finder（ダブルクリック）:** `start-local.command` を実行（初回は右クリック → 開く が必要な場合あり）。

起動後、ブラウザで `http://localhost:3000/taskpane.html` を開き、一覧が表示されるか確認できます。

## Excel（Mac）で sideload する

Excel のバージョンによりメニュー名が異なる場合があります。次のいずれかで **マニフェストファイル** を指定します。

1. **挿入** → **アドイン**（または「オフィス アドイン」）→ **マイ アドイン**
2. **アドインをアップロード**／**マニフェストから追加** などの項目で、次を指定する  
   **`manifest.local.xml`**（このフォルダ内の実ファイル）

詳細は Microsoft 公式「Office アドインのテスト」を参照してください。

## トラブル時

- タスクペインが真っ白・読み込み失敗: 先にローカルサーバーを起動し、`taskpane.html` がブラウザで開けるか確認する。
- `http://localhost` が拒否される環境では、開発用 HTTPS や別ホストへの変更が必要になることがあります。その場合は `manifest.prod.sample.xml` を参考に URL を組み替えてください。

## 未実装（意図的）

ログイン、DB、サーバ通信、投稿の永続化、Excel セル操作、本番用ビルドパイプライン。
