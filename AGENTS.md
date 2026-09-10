<!--
STANDARD_ID: SCAO-AI-GOVERNANCE-AGENTS
STANDARD_VERSION: 1.0
SOURCE: solacom_main/docs/standards/project-bootstrap/AGENTS.md
DISTRIBUTION_MODE: COPY_FROM_CENTRAL_SSOT
LOCAL_EDIT_POLICY: PROHIBITED
-->

# Agent Operating Procedures

## Required Reading Order

作業開始前に次の順番で読む。

1. `/CONSTITUTION.md`
2. `/docs/design/README.md`
3. `/docs/design/01_REQUEST_DEFINITION.md`
4. `/docs/design/02_REQUIREMENTS_DEFINITION.md`
5. `/docs/design/03_DATA_AND_SECURITY_DESIGN.md`
6. `/docs/design/04_UI_AND_FLOW_DESIGN.md`
7. `/docs/design/05_ARCHITECTURE_DESIGN.md`
8. `/docs/design/06_OPERATION_AND_HANDOFF.md`
9. 作業対象機能の仕様正本
10. 今回の承認済み作業指示書
11. 関連コード
12. 関連テスト

存在しない文書がある場合、標準導入ゲート違反として停止する。

## 文書優先順位

```text
ユーザーの最新明示指示
→ CONSTITUTION.md
→ プロジェクト固有の確定仕様
→ 対象機能の仕様正本
→ 承認済み作業指示書
→ 既存実装
→ コメント・補助資料
```

文書間に矛盾がある場合、実装担当 AI が独自に優先順位を適用して変更してはならない。

矛盾箇所を報告し停止する。

## 仕様書編集の 2 段階手順

### Phase A：事前分析

- ファイルを変更しない
- F/O/R 台帳を確認する
- 変更予定一覧を作成する
- 各変更の対象 ID を示す
- 分類変更の有無を示す
- 指定外論点の有無を示す
- 設計担当の承認前に編集しない

報告形式：

```markdown
| ID | 現在の位置 | 変更予定位置 | 分類変更 | 内容変更 | 判定 |
|---|---|---|---|---|---|
```

### Phase B：編集

- 承認済み一覧にある変更だけを実施する
- 承認一覧にない変更を行わない
- 新規 F/O/R-ID を追加しない
- 分類を変更しない
- 独自の整理、一般化、再設計を行わない
- 追加変更が必要になった場合は停止する

## 編集後の必須検査

### FROZEN

各 F-ID について次を確認する。

- 確定仕様台帳に存在する
- 状態が `FROZEN`
- 本文の確定仕様として存在する
- 未決事項に存在しない
- 却下仕様に存在しない
- 意味変更がない

### OPEN

各 O-ID について次を確認する。

- 未決事項台帳に存在する
- 状態が `OPEN`
- 確定仕様に存在しない
- 却下仕様に存在しない
- 未登録の OPEN 項目がない

### REJECTED

各 R-ID について次を確認する。

- 却下仕様台帳に存在する
- 状態が `REJECTED`
- 有効仕様として存在しない
- 却下理由が維持されている

## commit 前レビュー

- 実装担当 AI は commit しない
- 設計担当が `git diff`、F/O/R 全件照合、対象外差分非接触を確認する
- 1 件でも FAIL があれば commit 不可

## 応答生成と送信前ゲート

規範は `CONSTITUTION.md` §13 に従う。回答生成後、送信前に次を順番に判定する。

### Gate 1: 回答種別

次のどちらかを判定する。

```text
NORMAL_RESPONSE
DELIVERABLE_RESPONSE
```

成果物回答の条件は `CONSTITUTION.md` §13.4 に従う。

### Gate 2: 通常回答チェック

`NORMAL_RESPONSE` の場合、以下をすべて確認する。

- 冒頭で結論が理解できる
- 理由は判断に必要な要点へ限定されている
- 次の行動は原則 1 つである
- 内部調査過程を表示していない
- 網羅一覧を無条件に表示していない
- 成果物本文を混在させていない
- 同じ内容を繰り返していない
- 表が文章より簡潔である（表を使用した場合）
- ユーザーが求めていない背景説明を含めていない

1 項目でも不合格なら、回答を削るのではなく、通常回答として再構成する。

### Gate 3: 成果物回答チェック

`DELIVERABLE_RESPONSE` の場合、以下を確認する。

- 成果物として必要な情報が欠落していない
- 対象範囲が明確である
- 不要な会話説明が成果物へ混入していない
- 同じ指示が複数箇所で重複していない
- ユーザー向け要約と成果物本文が区別されている

### Gate 4: 再出力

ゲート不合格時は、`CONSTITUTION.md` §13.8 に従い、説明を追記せず回答全体を再生成する。

冗長化違反と内容上の誤りを混同しない。誤り訂正時のみ「間違いでした」を使用する。
