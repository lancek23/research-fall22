#!/usr/bin/env python

import numpy as np
import pickle

def binarize(first, second):

    with open("../mnist/mnist-test-images","rb") as f: test_images = pickle.load(f)
    with open("../mnist/mnist-train-images","rb") as f: train_images = pickle.load(f)
    with open("../mnist/mnist-test-labels","rb") as f: test_labels = pickle.load(f)
    with open("../mnist/mnist-train-labels","rb") as f: train_labels = pickle.load(f)

    # Drop unnecessary values
    train_images_A = train_images[train_labels == first]
    train_images_B = train_images[train_labels == second]
    train_images = np.append(train_images_A, train_images_B, axis=0)
    train_labels_A = train_labels[train_labels == first]
    train_labels_B = train_labels[train_labels == second]
    train_labels = np.append(train_labels_A, train_labels_B, axis=0)

    test_images_A = test_images[test_labels == first]
    test_images_B = test_images[test_labels == second]
    test_images = np.append(test_images_A, test_images_B, axis=0)
    test_labels_A = test_labels[test_labels == first]
    test_labels_B = test_labels[test_labels == second]
    test_labels = np.append(test_labels_A, test_labels_B, axis=0)

    for image in test_images:
        image[image < 0.5] = 0
        image[image >= 0.5] = 1
    for image in train_images:
        image[image < 0.5] = 0
        image[image >= 0.5] = 1

    test_images = test_images.astype(int)
    test_labels = (test_labels == second).astype(int)
    train_images = train_images.astype(int)
    train_labels = (train_labels == second).astype(int)

    filepathStart = "Digit datasets/binarize-normal/mnist-"
    with open(''.join((filepathStart, str(first), "-", str(second), "-test-images")), "wb") as f: pickle.dump(test_images,f)
    with open(''.join((filepathStart, str(first), "-", str(second), "-train-images")), "wb") as f: pickle.dump(train_images, f)
    with open(''.join((filepathStart, str(first), "-", str(second), "-test-labels")), "wb") as f: pickle.dump(test_labels, f)
    with open(''.join((filepathStart, str(first), "-", str(second), "-train-labels")), "wb") as f: pickle.dump(train_labels, f)

    filepathStart = "Digit datasets/binarize-normal/"
    new_train = open(''.join((filepathStart, "training-", str(first), "-", str(second), ".txt")), "w")
    new_test = open(''.join((filepathStart, "testing-", str(first), "-", str(second), ".txt")), "w")

    for image, label in zip(train_images, train_labels):

        for val in image:
            new_train.write(str(val))
            new_train.write(",")
        new_train.write(str(label))
        new_train.write("\n")

    for image, label in zip(test_images, test_labels):
        for val in image:
            new_test.write(str(val))
            new_test.write(",")
        new_test.write(str(label))
        new_test.write("\n")