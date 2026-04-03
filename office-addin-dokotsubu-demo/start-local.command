#!/usr/bin/env bash
#
# ソース名: start-local.command
# Lang: Shell
# Function: Finder からダブルクリックでローカル静的サーバー（ポート 3000）を起動する
# Note: start-local.sh と同じ処理。ゲートキーメッセージが出たら右クリック→開くで試す。
# Author:Takashi Oikawa
# Date:2026/04/04
# LastUp:2026/04/04
#
cd "$(dirname "$0")"
exec bash ./start-local.sh
