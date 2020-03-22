class TddBmiCalc_And_BubbleSort:

    @staticmethod
    def CalcBmi(height, weight):
        return float("{0: .1f}".format(weight / (height * height)))
