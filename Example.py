import tkinter.filedialog as fd
from tkinter import *

key = 100
def Encrypt():
    file = open(filename, "rb")
    data = file.read()
    file.close()

    data = bytearray(data)
    for index, value in enumerate(data):
        data[index] = value ^ key

    file = open(filename, "wb")
    file.write(data)
    file.close()


def Decrypt():
    file = open(filename, "rb")
    data = file.read()
    file.close()

    data = bytearray(data)
    for index, value in enumerate(data):
        data[index] = value ^ key

    file = open(filename, "wb")
    file.write(data)
    file.close()


def select():
    global filename
    filename = fd.askopenfilename()
    label1.config(text=filename)

# --------------- 1. function ends


# -------------2. Tkinter code start
root = Tk()
root.geometry('300x300')
root.title("Cryptography")
label = Label(root, text="Select file to encrypt or decrypt")
selectfile = Button(root, text="Select a file", command=select)
label1 = Label(root, text="No file selected")

button_enc = Button(root, text="Encrypt/Decrypt", command=Encrypt)

label2 = Label(root, text="-")

label.pack()
label1.pack()
label2.pack()
selectfile.pack()
button_enc.pack()

root.mainloop()

# ------------------------Tkinter code ends
