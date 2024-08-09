package gg.essential.universal.standalone.render

internal object IndexBufferBuilder {
    private var quadsBuffer: ShortArray = ShortArray(0)
    private var triangleFanBuffer: ShortArray = ShortArray(0)

    /** Returns an index buffer for drawing [count] quads as triangles with glDrawElements. */
    fun forQuads(count: Int): ShortArray {
        val bufSize = count * 6
        if (quadsBuffer.size < bufSize) {
            val buf = ShortArray(bufSize)
            var index = 0
            for (i in 0 until bufSize step 6) {
                //  First triangle
                buf[i + 0] = (index + 0).toShort()
                buf[i + 1] = (index + 1).toShort()
                buf[i + 2] = (index + 2).toShort()
                // Second triangle
                buf[i + 3] = (index + 0).toShort()
                buf[i + 4] = (index + 2).toShort()
                buf[i + 5] = (index + 3).toShort()
                // Increment buffer index (4 vertices per quad)
                index += 4
            }
            quadsBuffer = buf
        }
        return quadsBuffer
    }

    /** Returns an index buffer for drawing [count] triangles arranged in a fan as individual triangles with glDrawElements. */
    fun forTriangleFan(count: Int): ShortArray {
        val bufSize = count * 3
        if (triangleFanBuffer.size < bufSize) {
            val buf = ShortArray(bufSize)
            var index = 1
            for (i in 0 until bufSize step 3) {
                buf[i + 0] = 0
                buf[i + 1] = (index + 0).toShort()
                buf[i + 2] = (index + 1).toShort()
                index++
            }
            triangleFanBuffer = buf
        }
        return triangleFanBuffer
    }
}