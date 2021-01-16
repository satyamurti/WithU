
import base64
from fastapi import FastAPI,File,UploadFile
from pydantic import BaseModel

class request_body(BaseModel):
    audio_string : str

app = FastAPI()


@app.get("/")
async def read_root():
    return {"Hello": "World"}


@app.post("/check")
async def isDanger(data : request_body):
    # encode data.audio_string
    return {data.audio_string}