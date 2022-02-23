import math

import cv2
import numpy as np

color_range = {
    "blue": [
        # Lower, Upper
        ([100, 65, 65], [130, 255, 255])
    ],
    "red": [
        # For red we need two ranges because the hue value wraps around
        ([176, 127, 65], [180, 255, 255]),
        ([0, 127, 65], [3, 255, 255])
    ]
}


def create_circular_mask(h, w, center=None, radius=None):

    if center is None: # use the middle of the image
        center = (int(w/2), int(h/2))
    if radius is None: # use the smallest distance between the center and image walls
        radius = min(center[0], center[1], w-center[0], h-center[1])

    Y, X = np.ogrid[:h, :w]
    dist_from_center = np.sqrt((X - center[0])**2 + (Y-center[1])**2)

    mask = dist_from_center <= radius
    return mask


def runPipeline(image, llrobot):
    color_to_detect = "blue"

    hsv_image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    color_ranges = color_range[color_to_detect]
    mask = np.zeros((image.shape[0], image.shape[1]))
    for crange in color_ranges:
        mask += cv2.inRange(hsv_image, np.array(crange[0]), np.array(crange[1]))
    contours, hierarchy = cv2.findContours(mask.astype(np.uint8), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    possible_circles = []

    for c in contours:
        center, radius = cv2.minEnclosingCircle(c)
        circle_area = math.pi * radius ** 2
        if circle_area > 2500:
            cir_density = cv2.contourArea(c) / circle_area
            possible_circles.append((cir_density, circle_area, (int(center[0]), int(center[1])), int(radius)))

    possible_circles.sort(key=lambda x: x[0], reverse=True)

    if len(possible_circles) > 0:
        circle_density, circle_area, center, radius = possible_circles[0]
        x, y = center

        # Plot the points of a circle
        # A dumb way of looping through 0% - 100%
        num_points = 50
        circle_contour = np.zeros((num_points, 1, 2))
        for i in range(0, num_points):
            rads = 2 * math.pi * (1 / num_points) * i
            cx = radius * math.cos(rads) + x
            cy = radius * math.sin(rads) + y
            circle_contour[i, 0, :] = np.asarray([cx, cy])

        return circle_contour.astype(int), image, llrobot

    else:

        return [], image, llrobot


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    camera = cv2.VideoCapture(0)
    while True:
        worked, image = camera.read()
        if worked:
            ratio = 640 / image.shape[1]
            image = cv2.resize(image, (640, int(image.shape[0] * ratio)), interpolation=cv2.INTER_LINEAR)

            contour, _, _ = runPipeline(image, None)
            if contour is not None and len(contour) > 0:
                cv2.drawContours(image, [contour], -1, (255, 0, 0), 5)

            cv2.imshow("Image", image)
            cv2.waitKey(1)
