import binarizeSymmetrically as bs
import binarizeImportantFeatures as bif
import binarizeBothWays as bw
import binarizeNormal as bn

for i in range(10):
    for j in range(i+1, 10):
        print(''.join((str(i), ", ", str(j))))
        bs.binarize(i, j)
        bif.binarize(i, j)
        bw.binarize(i, j)
        bn.binarize(i, j)