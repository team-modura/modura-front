import os
import requests

API_KEY = os.environ['GEMINI_API_KEY']
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
response_json = res.json()

# 오류 메시지 확인 및 예외 처리
if "error" in response_json:
    error_msg = response_json["error"].get("message", "Unknown error")
    review = f"Gemini API 호출 오류: {error_msg}"
elif "candidates" in response_json:
    review = response_json["candidates"][0]["content"]["parts"][0]["text"]
else:
    review = "Gemini API 응답 형식 오류 또는 결과 없음."

with open('.github/gemini_review_comment.txt', 'w') as f:
    f.write(review)