import matplotlib.pyplot as plt
import numpy as np
import csv

# DIGIT DATASET
# WEIGHT, GRADE, AVG LL, ACCURACY
# CLASSIFIED BY INTEGER GRADE AT VARYING WEIGHTS, FORCED TO USE ENTIRE BUDGET
# NORMAL LNB AVG LL = -508.19686354445224
# NORMAL LNB ACCURACY = 0.7801418439716312
fields = []
rows = np.empty((0,4))
with open("Generated Data\\classify-01-no-bottom-row.txt", 'r') as csvfile:
    csvreader = csv.reader(csvfile)
    fields = next(csvreader)
    for row in csvreader:
        rows = np.append(rows, np.array([row], dtype=float), axis=0)

figure, axis = plt.subplots(2, 2)
figure.suptitle('Digit dataset - Classified by integer grade at varying weights - Forced to use entire budget')

axis[0, 0].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 2])
axis[0, 0].set_ylabel("Avg LL")
axis[0, 0].title.set_text("Weight vs Avg LL")

axis[0, 1].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 3])
axis[0, 1].set_ylabel("Accuracy")
axis[0, 1].title.set_text("Weight vs Accuracy")

axis[1,0].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 2])
axis[1,0].set_xlabel("Weight")
axis[1,0].set_ylabel("Avg LL")
axis[1,0].title.set_text("Weight vs Avg LL")
axis[1,0].axhline(y=-508.19686354445224, linestyle='--', label='Normal LNB')
axis[1,0].legend(loc="best")

axis[1,1].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 3])
axis[1,1].set_xlabel("Weight")
axis[1,1].set_ylabel("Accuracy")
axis[1,1].title.set_text("Weight vs Accuracy")
axis[1,1].axhline(y=0.7801418439716312, linestyle='--', label='Normal LNB')
axis[1,1].legend(loc="best")

plt.show()

# DIGIT DATASET
# WEIGHT, GRADE, AVG LL, ACCURACY
# CLASSIFIED BY INTEGER GRADE AT VARYING WEIGHTS, USING IDEAL BUDGET
# NORMAL LNB AVG LL = -508.19686354445224
# NORMAL LNB ACCURACY = 0.7801418439716312
fields = []
rows = np.empty((0,4))
with open("Generated Data\\classify-01-with-bottom-row.txt", 'r') as csvfile:
    csvreader = csv.reader(csvfile)
    fields = next(csvreader)
    for row in csvreader:
        rows = np.append(rows, np.array([row], dtype=float), axis=0)

figure, axis = plt.subplots(2, 2)
figure.suptitle('Digit dataset - Classified by integer grade at varying weights - Using ideal budget')

axis[0, 0].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 2])
axis[0, 0].set_ylabel("Avg LL")
axis[0, 0].title.set_text("Weight vs Avg LL")

axis[0, 1].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 3])
axis[0, 1].set_ylabel("Accuracy")
axis[0, 1].title.set_text("Weight vs Accuracy")

axis[1,0].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 2])
axis[1,0].set_xlabel("Weight")
axis[1,0].set_ylabel("Avg LL")
axis[1,0].title.set_text("Weight vs Avg LL")
axis[1,0].axhline(y=-508.19686354445224, linestyle='--', label='Normal LNB')
axis[1,0].legend(loc="best")

axis[1,1].scatter(rows[0:rows.shape[0], 0], rows[0:rows.shape[0], 3])
axis[1,1].set_xlabel("Weight")
axis[1,1].set_ylabel("Accuracy")
axis[1,1].title.set_text("Weight vs Accuracy")
axis[1,1].axhline(y=0.7801418439716312, linestyle='--', label='Normal LNB')
axis[1,1].legend(loc="best")

plt.show()