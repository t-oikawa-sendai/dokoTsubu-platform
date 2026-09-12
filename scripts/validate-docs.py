#!/usr/bin/env python3
# Program Name: validate-docs.py
# Language: Python
# Function: Validate required Document Info headers in managed Markdown files
# Created: 2026-09-05
# Last Updated: 2026-09-12
# Author: Takashi Oikawa
# AI: Cursor Grok 4.6
# Memo: Header names match DOCUMENT_GOVERNANCE_STANDARD.md section 5.2. D-015 uses git check-ignore
# STANDARD_ID: SCAO-DOC-VALIDATOR
# STANDARD_VERSION: 1.1
# SOURCE: solacom_main/docs/standards/project-bootstrap/scripts/validate-docs.py
# DISTRIBUTION_MODE: COPY_FROM_CENTRAL_SSOT
# LOCAL_EDIT_POLICY: PROHIBITED

from __future__ import annotations

import os
import shutil
import subprocess
import sys
from pathlib import Path


REQUIRED_HEADERS = [
    "Document ID（文書ID）",
    "Version（バージョン）",
    "Status（ステータス）",
    "Created Date（作成日）",
    "Last Updated（最終更新日）",
    "Owner（管理者）",
    "Related Documents（関連文書）",
]

EXCLUDED_ROOT_FILES = {
    "AGENTS.md",
    "CONSTITUTION.md",
}

EXCLUDED_DIRECTORIES = {
    ".git",
    ".venv",
    ".venv_docgen",
    "node_modules",
    "target",
}


class GitIgnoreError(Exception):
    pass


def repository_root() -> Path:
    return Path(__file__).resolve().parent.parent


def ensure_git_available() -> None:
    if shutil.which("git") is None:
        raise GitIgnoreError("git executable not found")


def ensure_git_repository(root: Path) -> None:
    result = subprocess.run(
        ["git", "-C", str(root), "rev-parse", "--is-inside-work-tree"],
        capture_output=True,
        text=True,
    )
    if result.returncode != 0 or result.stdout.strip() != "true":
        raise GitIgnoreError("unable to confirm git repository: {0}".format(root))


def is_git_ignored(root: Path, relative_path: str) -> bool:
    result = subprocess.run(
        ["git", "-C", str(root), "check-ignore", "-q", "--", relative_path],
        capture_output=True,
        text=True,
    )
    if result.returncode == 0:
        return True
    if result.returncode == 1:
        return False
    raise GitIgnoreError("unable to evaluate git ignore for {0}".format(relative_path))


def list_doc_files(root: Path) -> list[Path]:
    ensure_git_available()
    ensure_git_repository(root)
    results: list[Path] = []
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames[:] = [name for name in dirnames if name not in EXCLUDED_DIRECTORIES]
        for filename in filenames:
            if not filename.endswith(".md"):
                continue
            path = Path(dirpath) / filename
            relative = path.relative_to(root).as_posix()
            if relative in EXCLUDED_ROOT_FILES:
                continue
            if is_git_ignored(root, relative):
                continue
            results.append(path)
    return sorted(results)


def missing_headers(text: str) -> list[str]:
    return [header for header in REQUIRED_HEADERS if header not in text]


def main() -> int:
    root = repository_root()
    try:
        files = list_doc_files(root)
    except GitIgnoreError as exc:
        print("FAIL: {0}".format(exc))
        return 1

    if not files:
        print("FAIL: no managed markdown files found")
        return 1

    failed = False
    for path in files:
        relative = path.relative_to(root).as_posix()
        missing = missing_headers(path.read_text(encoding="utf-8"))
        if missing:
            failed = True
            print("FAIL: {0}".format(relative))
            for header in missing:
                print("  missing: {0}".format(header))
        else:
            print("PASS: {0}".format(relative))

    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
