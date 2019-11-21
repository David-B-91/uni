#!/usr/bin/env python

"""Description:
The train.py is to build your CNN model, train the model, and save it for later evaluation(marking)
This is just a simple template, you feel free to change it according to your own style.
However, you must make sure:
1. Your own model is saved to the directory "model" and named as "model.h5"
2. The "test.py" must work properly with your model, this will be used by tutors for marking.
3. If you have added any extra pre-processing steps, please make sure you also implement them in "test.py" so that they can later be applied to test images.

©2019 Created by Yiming Peng and Bing Xue
"""
from keras import backend as K

import numpy as np
import tensorflow as tf
import random
import keras

from keras.callbacks import EarlyStopping
from tensorflow.keras.callbacks import EarlyStopping
from keras.preprocessing.image import ImageDataGenerator
from keras.layers import Activation, Conv2D, Flatten, Dense, MaxPooling2D, Dropout
from keras.models import Sequential

# Set random seeds to ensure the reproducible results
SEED = 309
np.random.seed(SEED)
random.seed(SEED)
if tf.__version__ < "2.0.0":
    tf.set_random_seed(SEED)
else:
    tf.random.set_seed(SEED)

# training settings
img_width, img_height = 128, 128
zoom_range = 0.2
batch_size = 16
epoch_size = 100
train_samples = 4197
validation_samples = 1500
num_classes = 3
train_dir = "data/train"


def construct_model():
    """
    Construct the CNN model.
    ***
        Please add your model implementation here, and don't forget compile the model
        E.g., model.compile(loss='categorical_crossentropy',
                            optimizer='sgd',
                            metrics=['accuracy'])
        NOTE, You must include 'accuracy' in as one of your metrics, which will be used for marking later.
    ***
    :return: model: the initial CNN model
    """
    model = Sequential()

    # input layer
    model.add(Conv2D(filters=32, kernel_size=3, activation='relu', input_shape=(img_width, img_height, 3)))
    model.add(MaxPooling2D(pool_size=2))

    model.add(Conv2D(filters=32, kernel_size=3, activation='relu'))
    model.add(MaxPooling2D(pool_size=2))

    model.add(Conv2D(filters=64, kernel_size=3, activation='relu'))
    model.add(Conv2D(filters=64, kernel_size=3, activation='relu'))
    model.add(MaxPooling2D(pool_size=2))
    model.add(Dropout(0.2))


    # output layer
    model.add(Flatten())
    model.add(Dense(64, activation='relu'))
    model.add(Dropout(0.3))
    model.add(Dense(3, activation='softmax'))

    opt = keras.optimizers.Adam(learning_rate=0.0005, beta_1=0.9, beta_2=0.999, amsgrad=False)

    model.compile(loss='categorical_crossentropy',
              optimizer=opt,
              metrics=['accuracy'])
    return model


def train_model(model):
    """
    Train the CNN model
    ***
        Please add your training implementation here, including pre-processing and training
    ***
    :param model: the initial CNN model
    :return:model:   the trained CNN model
    """
    # Add your code here
    train, validate = preprocess()
    cbs = [EarlyStopping(monitor="val_accuracy", min_delta=0.005, patience=4, mode="auto")]
    model.fit_generator(train,
                        steps_per_epoch=train_samples//batch_size,
                        validation_data=validate,
                        validation_steps=validation_samples//batch_size,
                        epochs=epoch_size,
                        callbacks=cbs)
    return model


def save_model(model):
    """
    Save the keras model for later evaluation
    :param model: the trained CNN model
    :return:
    """
    # ***
    #   Please remove the comment to enable model save.
    #   However, it will overwrite the baseline model we provided.
    # ***
    model.save("model/model.h5")
    print("Model Saved Successfully.")

def preprocess():
    datagen = ImageDataGenerator(rotation_range=40,
                                 width_shift_range=0.2,
                                 height_shift_range=0.2,
                                 rescale=1. / 255,
                                 zoom_range=[1-zoom_range, 1+zoom_range],
                                 horizontal_flip=True,
                                 vertical_flip=True,
                                 validation_split=0.2)

    train = datagen.flow_from_directory(directory=train_dir,
                                        target_size=(img_width,img_height),
                                        batch_size=batch_size,
                                        class_mode='categorical',
                                        color_mode='rgb',
                                        subset='training')

    validate = datagen.flow_from_directory(directory=train_dir,
                                           target_size=(img_width,img_height),
                                           class_mode='categorical',
                                           color_mode='rgb',
                                           subset='validation')
    return train, validate

if __name__ == '__main__':
    model = construct_model()
    model = train_model(model)
    save_model(model)
