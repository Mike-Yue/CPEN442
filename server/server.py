import websockets
import asyncio
import ssl

async def validatePin(websocket, path):
    pin = await websocket.recv()
    if pin == "1234":
        print("Yes")
    else:
        print("No {}".format(pin))

start_server = websockets.serve(validatePin, "localhost", "8000")

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
    