import websockets
import asyncio

async def sendPin():
    uri = "ws://localhost:8000"
    async with websockets.connect(uri) as websocket:
        pin = input("Enter Pin")
        await websocket.send(pin)

asyncio.get_event_loop().run_until_complete(sendPin())