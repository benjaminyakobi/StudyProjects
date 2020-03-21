import unittest
from TddBmiCalc_And_BubbleSort import TddBmiCalc_And_BubbleSort

class TddBmiCalcTest_And_BubbleSortTest(unittest.TestCase):
    def test_CalcBmi(self):
        # stub = height
        stub1 = 1.8
        stub2 = 1.7
        stub3 = 1.5
        stub4 = 1.9

        # assume = bmi
        expected1 = 21.6
        expected2 = 27.7
        expected3 = 26.7
        expected4 = 18.0

        # action
        result1 = TddBmiCalc_And_BubbleSort.CalcBmi(stub1, 70)
        result2 = TddBmiCalc_And_BubbleSort.CalcBmi(stub2, 80)
        result3 = TddBmiCalc_And_BubbleSort.CalcBmi(stub3, 60)
        result4 = TddBmiCalc_And_BubbleSort.CalcBmi(stub4, 65)

        # expect/assert
        self.assertEqual(result1, expected1)
        self.assertEqual(result2, expected2)
        self.assertEqual(result3, expected3)
        self.assertEqual(result4, expected4)

if __name__ == '__main__':
    unittest.main()