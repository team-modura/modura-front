import os
import requests

API_KEY = os.environ['gemini_pr_review']
with open("pr.diff", "r") as f:
    diff = f.read()

url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"
headers = {"Content-Type": "application/json"}
data = {
    "contents": [
        {"parts": [{"text": f"아래 코드 변경점을 리뷰해줘:\n{diff}"}]}
    ]
}
params = {"key": API_KEY}

res = requests.post(url, headers=headers, params=params, json=data)
review = res.json()["candidates"][0]["content"]["parts"][0]["text"]

with open('.github/gemini_review_comment.txt', 'w') as f:
    f.write(review)