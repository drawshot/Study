import gym
import numpy as np
import matplotlib.pyplot as plt
from gym.envs.registration import register
import random as pr

def rargmax(vector):

    m = np.amax(vector)
    indices = np.nonzero(vector == m)[0]
    return pr.choice(indices)

register(
    id='FrozenLake-v3',
    entry_point='gym.envs.toy_text:FrozenLakeEnv',
    kwargs={'map_name': '4x4',
            'is_slippery': True }
)

env = gym.make('FrozenLake-v3')

Q = np.zeros([env.observation_space.n, env.action_space.n])

# Discount factor
dis = .99
num_episodes = 2000

rList = []

for i in range(num_episodes):

    state = env.reset()
    rAll = 0
    done = False
    # print(Q)
    # print("----")

    while not done:
        # action = rargmax(Q[state, :])

        # Choose an action by greedily (with noise) picking from Q table
        action = np.argmax(Q[state, :] + np.random.randn(1, env.action_space.n) / (i + 1))

        new_state, reward, done, _ = env.step(action)

        Q[state, action] = reward + dis * np.max(Q[new_state, :])

        rAll += reward
        state = new_state
        # if done:
        #     print("done")

    rList.append(rAll)

print("Success rate: " + str(sum(rList)/num_episodes))
print("Final Q-Table Values")
print("LEFT DOWN RIGHT UP")
print(Q)
plt.bar(range(len(rList)), rList, color="blue")
plt.show()