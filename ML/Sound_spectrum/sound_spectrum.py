import numpy as np
from scipy.io import wavfile
import matplotlib.pyplot as plt
import pandas as pd
import tensorflow as tf

def load_wav_file(name, path):
    _, b = wavfile.read(path + name)
    assert _ == 44100
    return b

time_series = load_wav_file('breath(16bit).wav', './')

print(time_series[0:14])
print(time_series.shape)

plt.plot(time_series)
# plt.hlines(xmax=1000000, xmin=1000000, y=30000)
# plt.xlim(0,300000)
plt.show()