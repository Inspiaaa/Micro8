from pathlib import Path
import subprocess
from tqdm import tqdm

"""
Dependencies:
- ImageMagick (https://imagemagick.org/index.php)
- bit-field (https://github.com/wavedrom/bitfield)
"""


def render_bit_field(json_path, svg_path):
    svg = subprocess.check_output([
        "npx",
        "bit-field",
        "--lanes", "1",
        "--bits", "16",
        "--vflip",  # Display the least significant bit at the left.
        "--fontsize", "12"
        "--fontfamily", "Consolas",
        "-i", str(json_path)
    ], shell=True)

    with open(svg_path, "wb") as output_file:
        output_file.write(svg)


def convert_svg_to_png(svg_path, png_path):
    subprocess.call([
        "magick",
        "-density", "300",  # Increase DPI (otherwise the image is blurry)
        svg_path,
        png_path
    ], shell=True)


root = Path(".")
svg_path = Path("./svg")
png_path = Path("./png")

files = list(root.glob("*.json"))

for json_path in tqdm(files):
    name = json_path.stem
    svg_output_path = svg_path / (name + ".svg")
    png_output_path = png_path / (name + ".png")

    render_bit_field(json_path, svg_output_path)
    convert_svg_to_png(svg_output_path, png_output_path)
